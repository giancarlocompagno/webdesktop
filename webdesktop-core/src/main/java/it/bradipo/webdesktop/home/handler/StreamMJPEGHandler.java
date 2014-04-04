package it.bradipo.webdesktop.home.handler;

import it.bradipo.webdesktop.Screen;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;



public class StreamMJPEGHandler implements HttpHandler {
	
	
	public StreamMJPEGHandler(){
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		byte[] img = Screen.getScreen();
        response.send(img,"image/"+Screen.IMAGE_CODEC);
	}

}
