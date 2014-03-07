package it.bradipo.webdesktop.handler;


import it.bradipo.webdesktop.handler.home.util.CaratteriSpeciali;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class AbstractHttpHandler implements HttpHandler{
	
	
	
	
	public static Rectangle SCREEN_RECT = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	private Robot robot;
	private String hostName;
	private String sistemaOperativo;
	private boolean	readOnly;
	
	public AbstractHttpHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		this.hostName=hostName;
		this.robot = robot;
		this.readOnly=readOnly;
		this.sistemaOperativo=sistemaOperativo;
	}
	
	
	public String getHostName() {
		return hostName;
	}
	
	public Robot getRobot() {
		return robot;
	}
	
	public boolean isReadOnly() {
		return readOnly;
	}
	
	public String getSistemaOperativo() {
		return sistemaOperativo;
	}
	
	
	protected void sendOK(HttpExchange exchange) throws IOException{
		send(exchange, "<esito>ok</esito>", "text/html");
	}
	
	protected void sendHTML(HttpExchange exchange,String content) throws IOException{
		send(exchange, 200, content, new String[]{"text/html"});
	}
	
	protected void send(HttpExchange exchange,String content,String... contentType) throws IOException {
		send(exchange, 200, content, contentType);
	}
	
	protected void send(HttpExchange exchange,int responseCode, String content,String... contentType) throws IOException {
		send(exchange,responseCode, content.getBytes(),contentType);
	}
	
	
	protected void send(HttpExchange exchange,int responseCode, byte content[],String... contentType) throws IOException {
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
	
	protected void send(HttpExchange exchange, byte content[],String... contentType) throws IOException {
		send(exchange, 200, content, contentType);
	}
	
	
	
	/*protected static String getSubpath(String path) {
		int q = path.indexOf('?');
		String subpath = path.substring(q+1);
		return subpath;
	}*/
	
	
	protected static byte[] loadResources(InputStream is) throws IOException {
		ByteArrayOutputStream baosBody = new ByteArrayOutputStream();
		writeResources(is, baosBody);
		return baosBody.toByteArray();
	}
	
	protected static void writeResources(InputStream in,OutputStream responseBody) throws IOException{
		int read;
		byte buffer[] = new byte[4096];
		while ((read = in.read(buffer)) != -1){
			responseBody.write(buffer, 0, read);
		}
		in.close();
		responseBody.flush();
		responseBody.close();
	}
	
	
	protected byte[] loadResource(String resource) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(resource);
		return loadResources(is);
	}
	
	
	
	
	public class HttpRequest{
		
		public static final String URL_ENCODE = "ISO-8859-1";
		
		private Map<String,String> parametri = new HashMap<String, String>();
		private String uri;
		private String url;
		private String allParams;
		
		public HttpRequest(HttpExchange exchange) throws IOException{
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
		}
		
		public String getUri(){
			return uri;
		}
		
		public String getUrl() {
			return url;
		}
		
		public String getAllParams() {
			return allParams;
		}
		
		public String getParametro(String parametro) {
			return parametri.get(parametro);
		}
		
		public Set<String> getKeys() {
			return parametri.keySet();
		}
		
		public Collection<String> getValues() {
			return parametri.values();
		}
		
	}
	
}

