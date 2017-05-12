package com.andreidadushko.tomography2017.webapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpRequestHandler;
import org.xlightweb.IHttpResponse;
import org.xlightweb.IHttpResponseHandler;
import org.xlightweb.client.HttpClient;
import org.xsocket.Execution;
import org.xsocket.ILifeCycle;

public class LoadBalancerHandler implements IHttpRequestHandler, ILifeCycle {
	private final List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
	private int serverIdx = 0;
	private HttpClient httpClient;

	public LoadBalancerHandler(InetSocketAddress... realServers) {
		servers.addAll(Arrays.asList(realServers));
	}

	public void onInit() {
		httpClient = new HttpClient();
		httpClient.setAutoHandleCookies(false);
	}

	public void onDestroy() throws IOException {
		httpClient.close();
	}

	public void onRequest(final IHttpExchange exchange) throws IOException {
		
		IHttpRequest request = exchange.getRequest();

		InetSocketAddress serverAddr = servers.get(nextServerSlot());;
		IHttpResponseHandler respHdl = new IHttpResponseHandler() {

			@Execution(Execution.NONTHREADED)
			public void onResponse(IHttpResponse response) throws IOException {
				exchange.send(response);
			}

			@Execution(Execution.NONTHREADED)
			public void onException(IOException ioe) throws IOException {
				exchange.sendError(ioe);
			}
			
		};		

		// update the Request-URL of the request
		URL url = request.getRequestUrl();
		URL newUrl = new URL(url.getProtocol(), serverAddr.getHostName(), serverAddr.getPort(), url.getFile());
		request.setRequestUrl(newUrl);
		System.out.println("---------------");
		System.out.println(newUrl);	
		System.out.println(request.getMethod());
		System.out.println(request.getNonBlockingBody());
		System.out.println("---------------");
		// proxy header handling (remove hop-by-hop headers, ...)
		// ...
		//
		// forward the request
		httpClient.send(request, respHdl);
	}

	// get the next slot by using the using round-robin approach
	private synchronized int nextServerSlot() {
		serverIdx++;
		if (serverIdx >= servers.size()) {
			serverIdx = 0;
		}
		return serverIdx;
	}
}
