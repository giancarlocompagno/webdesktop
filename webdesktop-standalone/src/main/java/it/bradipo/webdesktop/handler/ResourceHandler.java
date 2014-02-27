package it.bradipo.webdesktop.handler;

import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;

public class ResourceHandler extends AbstractHttpHandler {
	
	
	public ResourceHandler(String hostName,Robot robot,boolean readOnly) {
		super(hostName,robot,readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		URI requestURI = exchange.getRequestURI();
		if("/".equals(requestURI.getPath())){
			sendMainPage(exchange);
		}else{
			send(exchange,loadResource(requestURI.getPath()));
		}
	}

	
	

	
}
