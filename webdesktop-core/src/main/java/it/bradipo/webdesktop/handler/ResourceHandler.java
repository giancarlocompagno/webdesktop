package it.bradipo.webdesktop.handler;

import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;

import static it.bradipo.webdesktop.handler.util.Util.*;

public class ResourceHandler implements HttpHandler {
	
	public ResourceHandler() {
		super();
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		String requestURL = request.getUrl();
		String file = requestURL.substring("/resources".length());
		response.send(loadResource(file));
	}

	
	

	
}
