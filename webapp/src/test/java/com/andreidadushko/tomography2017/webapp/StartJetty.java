package com.andreidadushko.tomography2017.webapp;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.management.MBeanServer;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.xlightweb.server.HttpServer;

/**
 * Separate startup class for people that want to run the examples directly. Use
 * parameter -Dcom.sun.management.jmxremote to startup JMX (and e.g. connect
 * with jconsole).
 */
public class StartJetty {
	/**
	 * Main function, starts the jetty server.
	 *
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {

		startInstance(8081);
		startInstance(8082);
		startInstance(8083);
		startInstance(8084);
		InetSocketAddress[] srvs = new InetSocketAddress[] { 
				new InetSocketAddress("127.0.0.1", 8081),
				new InetSocketAddress("127.0.0.1", 8082), 
				new InetSocketAddress("127.0.0.1", 8083),
				new InetSocketAddress("127.0.0.1", 8084) };

		LoadBalancerHandler hdl = new LoadBalancerHandler(srvs);
		HttpServer loadBalancer = null;
		try {
			loadBalancer = new HttpServer("127.0.0.1", 8080, hdl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadBalancer.run();
	}

	private static void startInstance(int port) {
		Server server = new Server();

		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setOutputBufferSize(32768);

		ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
		http.setPort(port);
		http.setIdleTimeout(1000 * 60 * 60);

		server.addConnector(http);

		WebAppContext bb = new WebAppContext();
		bb.setServer(server);
		bb.setContextPath("/");
		bb.setWar("src/main/webapp");

		server.setHandler(bb);

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		server.addEventListener(mBeanContainer);
		server.addBean(mBeanContainer);

		try {
			server.start();
			// server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}
