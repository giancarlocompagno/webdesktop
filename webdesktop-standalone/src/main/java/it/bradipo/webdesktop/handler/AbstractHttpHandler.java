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
	
	protected void send(HttpExchange exchange, String content,String... contentType) throws IOException {
		if(contentType.length==0){
			send(exchange, content.getBytes(),"html");
		}else{
			send(exchange, content.getBytes(),contentType);
		}
	}
	
	protected void send(HttpExchange exchange, byte content[],String... contentType) throws IOException {
		List<String> arrayList = new ArrayList<String>();
		for(int i=0;i<contentType.length;i++){
			arrayList.add(contentType[i]);
		}
		if(arrayList.size()>0){
			exchange.getResponseHeaders().put("Content-Type", arrayList);
		}
		exchange.sendResponseHeaders(200, content.length);
		exchange.getResponseBody().write(content);
		exchange.close();
	}
	
	
	
	protected void sendMainPage(HttpExchange exchange) throws IOException {
		  String s = getTemplate(getHostName(),isReadOnly());
	      send(exchange,s);
	   }

	private static String getTemplate(String hostName,boolean readOnly) {
		
		String ss = "var keydownup ={};\n";
		for(Integer x : CaratteriSpeciali.keydownupJavaKeyCodes()){
			ss = ss + "keydownup["+x+"] = false;\n"; 
		}
		
		String s =
	    		  "<html>\n"+
	    		  "<head>\n"+
	    		  "<title>" + hostName + " Desktop "+(readOnly?"(Read Only)":"")+"</title>"+
	    		  (readOnly?"":"<link rel=\"stylesheet\" href=\"/resources/bootstrap/css/bootstrap.min.css\" />")+
	    		  (readOnly?"":"<link rel=\"stylesheet\" href=\"/resources/bootstrap/css/bootstrap-theme.min.css\" />")+
	    		  (readOnly?"":"<link rel=\"stylesheet\" href=\"/resources/tree/themes/style.min.css\" />")+
	    		  "<script>\n"+
	    		  ss+
	    		  "</script>\n"+
	    		  "<script src=\"/resources/jquery.min.js\"></script>\n"+
	    		  (readOnly?"":"<script src=\"/resources/tree/libs/jquery.ui.touch.js\"></script>\n")+
	    		  (readOnly?"":"<script src=\"/resources/tree/libs/required.js\"></script>\n")+
	    		  (readOnly?"":"<script src=\"/resources/tree/libs/jstree.min.js\"></script>\n")+
	    		  "<script src=\"/resources/custom1.js\"></script>\n"+
	    		  (readOnly?"":"<script src=\"/resources/custom2.js\"></script>\n")+
	    		  "</head>\n"+
	    		  "<body>\n"+
	    		  (readOnly?"":"<form action=\"/upload\" method=\"post\" enctype=\"multipart/form-data\">"
	    		  			 + "<input type=\"file\" name=\"file\"/><input type=\"submit\" name=\"invia\"/></form>")+
	    		  (readOnly?"":"<div id=\"jstree\">"+
	    				  			
	    				  	"</div>")+
	    		  "<img id=\"screen\" src=\"/screen\" />"+
	    		  "</body>\n"+
	    		  "</html>\n";
		return s;
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
		this.getClass().getResource("/");
		InputStream is = this.getClass().getResourceAsStream(resource);
		return loadResources(is);
	}
}
