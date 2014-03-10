package it.bradipo.webdesktop.http;

import it.bradipo.webdesktop.handler.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sun.net.httpserver.HttpExchange;

public class HttpResponse extends AbstractHttpResponse {
	
	HttpExchange exchange;
	
	public HttpResponse(HttpExchange exchange) {
		this.exchange=exchange;
	}
	
	@Override
	public void send(int responseCode, byte content[],String... contentType) throws IOException {
		List<String> arrayList = new ArrayList<String>();
		for(int i=0;i<contentType.length;i++){
			arrayList.add(contentType[i]);
		}
		if(arrayList.size()>0){
			exchange.getResponseHeaders().put("Content-Type", arrayList);
		}
		exchange.sendResponseHeaders(responseCode, content.length);
		exchange.getResponseBody().write(content);
		exchange.close();
	}

	@Override
	public void send(int responseCode,long length, InputStream is, Map<String,String> header,String... contentType) throws IOException {
		if(header!=null){
			for(Entry<String,String> e : header.entrySet()){
				exchange.getResponseHeaders().set(e.getKey(),e.getValue());
			}
		}
		
		List<String> arrayList = new ArrayList<String>();
		if(contentType.length>0){
			for(String curr : contentType){
				arrayList.add(curr);
			}
			exchange.getResponseHeaders().put("Content-Type", arrayList);
		}
		
		exchange.sendResponseHeaders(responseCode,length);
		Util.writeResources(is, exchange.getResponseBody());
		
	}
}
