package it.bradipo.webdesktop;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ProxyHandler;
import it.bradipo.webdesktop.handler.util.HandlerManager;

import java.net.InetSocketAddress;
import java.util.Map.Entry;

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
