package it.bradipo.webdesktop.handler;

import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

public class JsonTreeHandler extends AbstractHttpHandler {
	
	
	public JsonTreeHandler(String hostName,Robot robot,boolean readOnly) {
		super(hostName,robot,readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try{
		String path = exchange.getRequestURI().toString();
		path = URLDecoder.decode(path,"ISO-8859-1");
		File file = null;
		File[] fs = null;
		String filename = path.substring(path.indexOf("?id=")+4);
		boolean isRoot = false;
		 
		if(filename.equals("#")){
			fs  = File.listRoots();
			isRoot = true;
		}else{
			file = new File(filename);
			if(file.isDirectory()){
				fs  = file.listFiles();	
			}else{
				
			}
		}

		StringBuilder s = new StringBuilder();
		if(fs!=null){
			s.append("[ \n");
			boolean isFirst = true;
			for(File f : fs){
				s.append("\t{ ");
				s.append(" \"id\":\""+convert(f)+"\", ");
				if(f.isDirectory()){
					s.append(" \"children\":\"true\" ");
				}else{
					s.append(" \"children\":\"false\" ");
				}
				if(isFirst){
					s.append(" \"type\":\"root\" ");
				}
				s.append((isRoot?f.toString():f.getName())+"</li>\n");
			}
			s.append("</ul>\n");
			System.out.println(s);
			send(exchange, s.toString(),"application/html");
		}else if(file!=null){
			FileInputStream in = new FileInputStream(file);
			byte[] content = loadResources(in);
			exchange.getResponseHeaders().set("content-disposition", "attachment;filename=" + file.getName());
			send(exchange, content, "application/octet-stream");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static int i = 0;
	
	public static String convert(File f) throws IOException{
		return f.toString();
	}

}
