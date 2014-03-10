package it.bradipo.webdesktop.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequest implements IHttpRequest{

	public static final String URL_ENCODE = "ISO-8859-1";

	private Map<String,String> parametri = new HashMap<String, String>();
	private String uri;
	private String url;
	private String allParams;
	private HttpExchange exchange;
	
	private IHttpHeaderRequest header;
	
	public HttpRequest(HttpExchange exchange) throws IOException{
		this.exchange=exchange;
		this.uri = exchange.getRequestURI().toString();
		this.url = uri;
		this.allParams = "";
		int index = uri.indexOf("?");
		if(index>=0){
			this.url = this.uri.substring(0,index);
			this.allParams = this.uri.substring(index+1);
			String[] params = this.allParams.split("&");
			for(int i=0;i<params.length;i++){
				String currParam = params[i];
				String[] kv = currParam.split("=");
				if(kv.length==2){
					parametri.put(kv[0], URLDecoder.decode(kv[1],URL_ENCODE));
				}else if(kv.length==1 && currParam.endsWith("=")){
					parametri.put(kv[0],"");
				}else if(kv.length==1 && currParam.startsWith("=")){
					parametri.put("",kv[0]);
				}
			}
		}
		header = new HttpHeaderRequest(exchange);
	}

	@Override
	public String getUri(){
		return uri;
	}
	
	@Override
	public String getUrl() {
		return url;
	}
	
	@Override
	public String getAllParams() {
		return allParams;
	}
	
	@Override
	public String getParametro(String parametro) {
		return parametri.get(parametro);
	}

	@Override
	public Collection<String> getKeys() {
		return parametri.keySet();
	}

	@Override
	public Collection<String> getValues() {
		return parametri.values();
	}
	
	
	@Override
	public IHttpHeaderRequest getHttpHeaderRequest() {
		return header;
	}
	
	
	@Override
	public InputStream getInputStream() {
		return exchange.getRequestBody();
	}

}