package it.bradipo.webdesktop.servlet.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import it.bradipo.webdesktop.http.IHttpHeaderRequest;
import it.bradipo.webdesktop.http.IHttpRequest;

public class HttpRequest implements IHttpRequest{
	
	private HttpServletRequest request;
	private String uri;
	private String url;
	private String allParams;
	private HttpRequestHeader header;
	
	
	private Map<String,String[]> maps = new HashMap<String, String[]>();
	
	public HttpRequest(HttpServletRequest request) {
		this.maps.putAll(request.getParameterMap());
		
		this.uri = request.getRequestURI();
		this.url = request.getRequestURL().toString();
		this.allParams = "";
		int index = uri.indexOf("?");
		if(index>=0){
			this.allParams = this.uri.substring(index+1);
		}
		this.header = new HttpRequestHeader(request);
	}
	

	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getAllParams() {
		return this.allParams;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return request.getInputStream();
	}

	@Override
	public String getParametro(String parametro) {
		String[] s = maps.get(parametro);
		return s.length>0?s[0]:null;
	}

	@Override
	public Collection<String> getKeys() {
		return maps.keySet();
	}

	@Override
	public Collection<String> getValues() {
		Collection<String> s = new ArrayList<String>();
		for(String key : getKeys()){
			s.add(getParametro(key));
		}
		return s;
	}

	@Override
	public IHttpHeaderRequest getHttpHeaderRequest() {
		// TODO Auto-generated method stub
		return header;
	}

}
