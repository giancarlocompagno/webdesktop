package it.bradipo.webdesktop.handler;

import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;

public interface HttpHandler {
	
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException;

}
