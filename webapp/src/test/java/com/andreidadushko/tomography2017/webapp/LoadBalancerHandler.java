package com.andreidadushko.tomography2017.webapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpRequest;
import org.xlightweb.IHttpRequestHandler;
import org.xlightweb.IHttpResponse;
import org.xlightweb.IHttpResponseHandler;
import org.xlightweb.client.HttpClient;
import org.xsocket.Execution;
import org.xsocket.ILifeCycle;

public class LoadBalancerHandler implements IHttpRequestHandler, ILifeCycle {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancerHandler.class);

	private final List<InetSocketAddress> servers = new ArrayList<InetSocketAddress>();
	private int serverIdx = 0;
	private HttpClient httpClient;

	public LoadBalancerHandler(InetSocketAddress... realServers) {
		servers.addAll(Arrays.asList(realServers));
	}

	@Override
	public void onInit() {
		httpClient = new HttpClient();
		httpClient.setAutoHandleCookies(false);
	}

	@Override
	public void onDestroy() throws IOException {
		httpClient.close();
	}

	@Override
	public void onRequest(final IHttpExchange exchange) throws IOException {
		IHttpRequest request = exchange.getRequest();

		InetSocketAddress serverAddr = servers.get(nextServerSlot());
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
		URL url = request.getRequestUrl();
		URL newUrl = new URL(url.getProtocol(), serverAddr.getHostName(), serverAddr.getPort(), url.getFile());
		request.setRequestUrl(newUrl);
		LOGGER.info("Load balancer send reqest to {}", newUrl);
		httpClient.send(request, respHdl);
	}

	private synchronized int nextServerSlot() {
		serverIdx++;
		if (serverIdx >= servers.size()) {
			serverIdx = 0;
		}
		return serverIdx;
	}
}
