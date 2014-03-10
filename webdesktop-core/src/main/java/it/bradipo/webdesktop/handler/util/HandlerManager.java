package it.bradipo.webdesktop.handler.util;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ResourceHandler;
import it.bradipo.webdesktop.handler.home.CommandHandler;
import it.bradipo.webdesktop.handler.home.HomePageHandler;
import it.bradipo.webdesktop.handler.home.StreamMJPEGHandler;
import it.bradipo.webdesktop.handler.shell.ShellEditHandler;
import it.bradipo.webdesktop.handler.shell.ShellPageHandler;
import it.bradipo.webdesktop.handler.task.TaskManagerPageHandler;
import it.bradipo.webdesktop.handler.tree.TreeHandler;
import it.bradipo.webdesktop.handler.tree.TreePageHandler;
import it.bradipo.webdesktop.handler.tree.UploadHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HandlerManager {
	
	
	
	private static Map<String,HttpHandler> map = new HashMap<String, HttpHandler>();

	static{
		map.put("/", new HomePageHandler());
		map.put("/resources", new ResourceHandler());
		map.put("/screen", new StreamMJPEGHandler());
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
	
	

}
