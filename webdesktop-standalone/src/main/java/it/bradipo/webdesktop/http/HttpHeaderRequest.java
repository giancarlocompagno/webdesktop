package it.bradipo.webdesktop.http;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class HttpHeaderRequest implements IHttpHeaderRequest {

	
	Map<String,List<String>> maps = new HashMap<String,List<String>>();
	
	public HttpHeaderRequest(HttpExchange exchange) {
		maps.putAll(exchange.getRequestHeaders());
	}
	
	
	@Override
	public List<String> get(String content) {
		return maps.get(content);
	}

	@Override
	public String getFirst(String content) {
		List<String> l = get(content);
		return l!=null && l.isEmpty()?null:l.get(0);
	}

	@Override
	public Collection<String> keys() {
		return maps.keySet();
	}

}
