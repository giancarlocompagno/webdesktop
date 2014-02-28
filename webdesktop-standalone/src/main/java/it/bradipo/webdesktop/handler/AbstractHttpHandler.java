package it.bradipo.webdesktop.handler;


import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class AbstractHttpHandler implements HttpHandler{
	
	
	
	
	public static Rectangle SCREEN_RECT = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	private Robot robot;
	private String hostName;
	private boolean	readOnly;
	
	public AbstractHttpHandler(String hostName,Robot robot,boolean readOnly) {
		this.hostName=hostName;
		this.robot = robot;
		this.readOnly=readOnly;
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
	
	protected void send(HttpExchange exchange,String content,String... contentType) throws IOException {
		send(exchange, 200, content, contentType);
	}
	
	protected void send(HttpExchange exchange,int responseCode, String content,String... contentType) throws IOException {
		if(contentType.length==0){
			send(exchange,responseCode, content.getBytes(),"html");
		}else{
			send(exchange,responseCode, content.getBytes(),contentType);
		}
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
	
	
	
	protected static String getSubpath(String path) {
		int q = path.indexOf('?');
		String subpath = path.substring(q+1);
		return subpath;
	}
	
	
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
}
