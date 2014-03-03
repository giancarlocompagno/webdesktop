package it.bradipo.webdesktop;

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

import java.awt.Robot;


import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Server {
	
	private HttpServer server;
	private String hostName;
	private Robot robot;
	private boolean readOnly;
	
	
	public Server(int serverPort,boolean readOnly) throws Exception {
		server = HttpServer.create(new InetSocketAddress(serverPort), 0);
		hostName = InetAddress.getLocalHost().getHostName();
		this.readOnly=readOnly;
		robot = new Robot();
		init();
	}
	
	
	private void init(){
		this.server.createContext("/", new HomePageHandler(hostName,robot,readOnly));
		this.server.createContext("/resources", new ResourceHandler(hostName,robot,readOnly));
		this.server.createContext("/screen", new StreamMJPEGHandler(hostName,robot,readOnly));
		if(!readOnly){
			this.server.createContext("/taskmanager.html", new TaskManagerPageHandler(hostName,robot,readOnly));
			this.server.createContext("/tree.html", new TreePageHandler(hostName,robot,readOnly));
			this.server.createContext("/shell.html", new ShellPageHandler(hostName,robot,readOnly));
			this.server.createContext("/shelledit", new ShellEditHandler(hostName,robot,readOnly));
			this.server.createContext("/command", new CommandHandler(hostName,robot,readOnly));
			this.server.createContext("/tree", new TreeHandler(hostName,robot,readOnly));
			this.server.createContext("/upload", new UploadHandler(hostName, robot, readOnly));
		}
	}
	
	public void start(){
		server.start();
	}
	
	public void stop(){
		server.stop(0);
	}
	
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(6060,true);
		server.start();
		System.out.println("in attesa");
	}
	
	
}
