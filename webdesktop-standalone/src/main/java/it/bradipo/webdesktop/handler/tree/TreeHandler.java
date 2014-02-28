package it.bradipo.webdesktop.handler.tree;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;

import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public class TreeHandler extends AbstractHttpHandler {
	
	
	public TreeHandler(String hostName,Robot robot,boolean readOnly) {
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
			s.append("<ul>\n");
			for(File f : fs){
				s.append("\t<li ");
				s.append(" id=\""+convert(f)+"\" ");
				String datajstree = null;
				if(isRoot && f.isDirectory()){
					s.append("class=\"jstree-closed\" ");
					datajstree = "\"icon\":\"/resources/css/tree/hd.png\"";
				}else if(isRoot){
					datajstree = "\"icon\":\"/resources/css/tree/hd.png\",\"disabled\":true ";
				}else if(f.isDirectory()){
					s.append("class=\"jstree-closed\" ");
				}else{
					datajstree = "\"icon\":\"/resources/css/tree/file.png\"";
				}
				if(datajstree!=null){
					s.append(" data-jstree='{"+datajstree+"}' ");	
				}
				s.append(">"+(isRoot?f.toString():f.getName())+" "+dimension(f)+"</li>\n");
			}
			s.append("</ul>\n");
			System.out.println(s);
			send(exchange, s.toString(),"application/html");
		}else if(file!=null){
			FileInputStream in = new FileInputStream(file);
			exchange.getResponseHeaders().set("content-disposition", "attachment;filename=" + file.getName());
			
			List<String> arrayList = new ArrayList<String>();
			arrayList.add("application/octet-stream");
			exchange.getResponseHeaders().put("Content-Type", arrayList);
			exchange.sendResponseHeaders(200,file.length());
			writeResources(in, exchange.getResponseBody());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static int i = 0;
	
	public static String convert(File f) throws IOException{
		return f.toString();
	}

	
	public String dimension(File f){
		StringBuilder size = new StringBuilder();
		if(f.isFile()){
			size.append( "(" );
		if(f.length()>=1024*1204*1024){
			String s = String.format("%.2g%n", (f.length()/(double)(1024*1024*1024)));
			size.append(s +" GB");
		}else if(f.length()>1024*1024){
			String s = String.format("%.2g%n", (f.length()/(double)(1024*1024)));
			size.append(s +" MB");
		}else if(f.length()>=1024){
			String s = String.format("%.2g%n", (f.length()/(double)(1024)));
			size.append(s +" KB");
		}else {
			size.append(f.length() +" B");
		}
		size.append( ")");
		}
		return size.toString();
	}
	
	
	
	
}
