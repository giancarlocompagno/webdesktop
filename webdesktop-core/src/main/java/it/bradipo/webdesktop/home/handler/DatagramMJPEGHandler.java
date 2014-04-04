package it.bradipo.webdesktop.home.handler;

import it.bradipo.webdesktop.Screen;
import it.bradipo.webdesktop.datagram.StorageVideoOut;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;


public class DatagramMJPEGHandler implements HttpHandler {
	
	
	
	public DatagramMJPEGHandler() throws IOException{
		
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		String uuid = request.getUsername();
		byte[] img = StorageVideoOut.getNextImage(uuid);
		response.send(img,"image/"+Screen.IMAGE_CODEC);
	}

}
