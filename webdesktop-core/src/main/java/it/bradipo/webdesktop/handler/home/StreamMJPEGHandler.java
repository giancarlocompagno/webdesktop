package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class StreamMJPEGHandler implements HttpHandler {
	
	
	private String IMAGE_CODEC = "jpeg";
	
	public StreamMJPEGHandler(){
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		BufferedImage im = ServerData.getInstance().getRobot().createScreenCapture(ServerData.getInstance().getScreenRect());
        ByteArrayOutputStream output = new ByteArrayOutputStream(500000);
        ImageIO.write(im, IMAGE_CODEC, output);
        response.send(output.toByteArray(),"image/"+IMAGE_CODEC);
	}

}
