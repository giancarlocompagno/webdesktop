package it.bradipo.webdesktop.servlet.handler;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.servlet.http.HttpRequest;
import it.bradipo.webdesktop.servlet.http.HttpResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyHandler{
	
	private HttpHandler handler;
	
	public ProxyHandler(HttpHandler handler) {
		this.handler=handler;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response)throws IOException {
		handler.handle(new HttpRequest(request),new HttpResponse(response));
	}

}
