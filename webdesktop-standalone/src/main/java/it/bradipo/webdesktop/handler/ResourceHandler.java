package it.bradipo.webdesktop.handler;

import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;

public class ResourceHandler extends AbstractHttpHandler {
	
	
	public ResourceHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		URI requestURI = exchange.getRequestURI();
		String file = requestURI.getPath().substring("/resources".length());
		send(exchange,loadResource(file));
	}

	
	

	
}
