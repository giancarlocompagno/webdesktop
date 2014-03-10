package it.bradipo.webdesktop.servlet.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import it.bradipo.webdesktop.handler.util.Util;
import it.bradipo.webdesktop.http.AbstractHttpResponse;

public class HttpResponse extends AbstractHttpResponse{
	
	private HttpServletResponse response;
	
	public HttpResponse(HttpServletResponse response) {
		this.response=response;
	}

	@Override
	public void send(int responseCode, byte[] content, String... contentType)throws IOException {
		for(int i=0;i<contentType.length;i++){
			response.setContentType(contentType[i]);
		}
		response.setStatus(responseCode);
		response.getOutputStream().write(content);
		response.flushBuffer();
	}

	@Override
	public void send(int responseCode, long length, InputStream is,Map<String, String> header, String... contentType)throws IOException {
		response.setStatus(responseCode);
		response.setContentLength((int)length);
		for(Entry<String,String> entry:header.entrySet()){
			response.addHeader(entry.getKey(), entry.getValue());
		}
		for(int i=0;i<contentType.length;i++){
			response.setContentType(contentType[i]);
		}
		Util.writeResources(is, response.getOutputStream());
	}

}
