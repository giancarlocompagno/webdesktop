package it.bradipo.webdesktop;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ProxyHandler;
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
import it.bradipo.webdesktop.handler.util.HandlerManager;

import java.awt.Robot;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.net.httpserver.HttpServer;

public class Server {
	
	public HttpServer server;


	public Server(int serverPort) throws Exception {
		this.server = HttpServer.create(new InetSocketAddress(serverPort), 0);
		init();
	}
	
	
	private void init() throws Exception{
		
		for(Entry<String, HttpHandler> entry : HandlerManager.getHttpHandlers()){
			this.server.createContext(entry.getKey(), new ProxyHandler(entry.getValue()));
		}
	}
	
	public void start(){
		server.start();
	}
	
	public void stop(){
		server.stop(0);
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Server server = new Server(6060);
		server.start();
		System.out.println("in attesa");
	}
	
	
}
