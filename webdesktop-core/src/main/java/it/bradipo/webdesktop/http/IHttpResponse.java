package it.bradipo.webdesktop.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface IHttpResponse {
	
	
	//public OutputStream getOutputStream();
	
	void sendOK() throws IOException;

	void sendHTML(String content) throws IOException;

	void send(String content, String... contentType) throws IOException;

	void send(int responseCode, String content, String... contentType)throws IOException;

	void send(byte[] content, String... contentType) throws IOException;

	void send(int responseCode, byte[] content, String... contentType)throws IOException;
	
	void send(int responseCode,long length,InputStream is,Map<String,String> header,String... contentType) throws IOException;
	
}