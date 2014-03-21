package it.bradipo.webdesktop.tree.handler;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class TreeHandler implements HttpHandler {
	
	
	public TreeHandler() {
		
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		try{
		File file = null;
		File[] fs = null;
		String filename = request.getParametro("id");
		boolean isRoot = false;
		 
		if(filename==null || filename.equals("#")){
			fs  = File.listRoots();
			isRoot = true;
		}else{
			file = new File(filename);
			if(file.isDirectory()){
				fs  = file.listFiles();	
			}else{
				
			}
		}
		String CONTEXT_PATH = request.getContextPath();
		StringBuilder s = new StringBuilder();
		if(fs!=null){
			s.append("<ul>\n");
			for(File f : fs){
				s.append("\t<li ");
				s.append(" id=\""+convert(f)+"\" ");
				String datajstree = null;
				if(isRoot && f.isDirectory()){
					s.append("class=\"jstree-closed\" ");
					datajstree = "\"icon\":\""+CONTEXT_PATH+"/resources/css/tree/hd.png\"";
				}else if(isRoot){
					datajstree = "\"icon\":\""+CONTEXT_PATH+"/resources/css/tree/hd.png\",\"disabled\":true ";
				}else if(f.isDirectory()){
					s.append("class=\"jstree-closed\" ");
				}else{
					datajstree = "\"icon\":\""+CONTEXT_PATH+"/resources/css/tree/file.png\"";
				}
				if(datajstree!=null){
					s.append(" data-jstree='{"+datajstree+"}' ");	
				}
				s.append(">"+(isRoot?f.toString():f.getName())+" "+dimension(f)+"</li>\n");
			}
			s.append("</ul>\n");
			response.sendHTML(s.toString());
		}else if(file!=null){
			FileInputStream in = new FileInputStream(file);
			
			HashMap<String, String> header = new HashMap<String, String>();
			header.put("content-disposition", "attachment;filename=\"" + file.getName()+"\"");
			response.send(200, file.length(), in, header, "application/octet-stream");
			
			
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
