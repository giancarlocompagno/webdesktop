package it.bradipo.webdesktop.handler.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {
	
	public static byte[] loadResources(InputStream is) throws IOException {
		ByteArrayOutputStream baosBody = new ByteArrayOutputStream();
		writeResources(is, baosBody);
		return baosBody.toByteArray();
	}
	
	public static void writeResources(InputStream in,OutputStream responseBody) throws IOException{
		int read;
		byte buffer[] = new byte[4096];
		while ((read = in.read(buffer)) != -1){
			responseBody.write(buffer, 0, read);
		}
		in.close();
		responseBody.flush();
		responseBody.close();
	}
	
	
	public static byte[] loadResource(String resource) throws IOException {
		InputStream is = Util.class.getResourceAsStream(resource);
		return loadResources(is);
	}

}
