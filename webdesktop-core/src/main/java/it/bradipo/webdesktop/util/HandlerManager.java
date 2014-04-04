package it.bradipo.webdesktop.util;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ResourceHandler;
import it.bradipo.webdesktop.home.handler.CommandHandler;
import it.bradipo.webdesktop.home.handler.DatagramMJPEGHandler;
import it.bradipo.webdesktop.home.handler.HomePageHandler;
import it.bradipo.webdesktop.home.handler.StreamMJPEGHandler;
import it.bradipo.webdesktop.shell.handler.ShellEditHandler;
import it.bradipo.webdesktop.shell.handler.ShellPageHandler;
import it.bradipo.webdesktop.task.handler.TaskManagerPageHandler;
import it.bradipo.webdesktop.tree.handler.TreeHandler;
import it.bradipo.webdesktop.tree.handler.TreePageHandler;
import it.bradipo.webdesktop.tree.handler.UploadHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HandlerManager {
	
	
	
	private static Map<String,HttpHandler> map = new HashMap<String, HttpHandler>();

	static{
		map.put("/resources", new ResourceHandler());
		//map.put("/screen", new StreamMJPEGHandler());
		map.put("/taskmanager.html", new TaskManagerPageHandler());
		map.put("/tree.html", new TreePageHandler());
		map.put("/shell.html", new ShellPageHandler());
		map.put("/shelledit", new ShellEditHandler());
		map.put("/command", new CommandHandler());
		map.put("/tree", new TreeHandler());
		map.put("/upload", new UploadHandler());
	}
	
	
	
	private HandlerManager() {
		
	}
	
	public static Set<Entry<String,HttpHandler>> getHttpHandlers(){
		return map.entrySet();
	}
	
	
	public static HttpHandler getDefault(){
		return new HomePageHandler();
	}
	
	public static HttpHandler getDatagram() throws IOException{
		return new DatagramMJPEGHandler();
	}
	
	public static HttpHandler getStream(){
		return new StreamMJPEGHandler();
	}

}
