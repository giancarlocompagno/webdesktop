package it.bradipo.webdesktop.servlet.http;

import it.bradipo.webdesktop.http.IHttpHeaderRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestHeader implements IHttpHeaderRequest{
	
	private Map<String,List<String>> maps = new HashMap<String,List<String>>();
	
	public HttpRequestHeader(HttpServletRequest request) {
		Enumeration<String> names = request.getHeaderNames();
		while(names.hasMoreElements()){
			String key = names.nextElement();
			Enumeration<String> en = request.getHeaders(key);
			List<String> values = new ArrayList<String>();
			while(en.hasMoreElements()){
				String value = en.nextElement();
				values.add(value);
			}
			maps.put(key.toLowerCase(), values);
		}
	}

	@Override
	public List<String> get(String content) {
		return maps.get(content);
	}

	@Override
	public String getFirst(String content) {
		List<String> l = get(content.toLowerCase());
		return l!=null && !l.isEmpty()?l.get(0):null;
	}

	@Override
	public Collection<String> keys() {
		return maps.keySet();
	}

}
