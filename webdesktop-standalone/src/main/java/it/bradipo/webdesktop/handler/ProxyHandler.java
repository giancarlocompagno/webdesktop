package it.bradipo.webdesktop.handler;

import it.bradipo.webdesktop.http.HttpRequest;
import it.bradipo.webdesktop.http.HttpResponse;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class ProxyHandler implements com.sun.net.httpserver.HttpHandler {
	
	HttpHandler proxy;
	
	public ProxyHandler(HttpHandler proxy) {
		this.proxy=proxy;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		proxy.handle(new HttpRequest(exchange), new HttpResponse(exchange));
	}

	

}
