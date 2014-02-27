package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.net.httpserver.HttpExchange;

public class StreamMJPEGHandler extends AbstractHttpHandler {
	
	
	private String IMAGE_CODEC = "jpeg";
	
	public StreamMJPEGHandler(String hostName,Robot robot,boolean readOnly) {
		super(hostName,robot,readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		BufferedImage im = getRobot().createScreenCapture(SCREEN_RECT);
        ByteArrayOutputStream output = new ByteArrayOutputStream(500000);
        ImageIO.write(im, IMAGE_CODEC, output);
        send(exchange, output.toByteArray());
	}

}
