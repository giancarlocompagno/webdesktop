package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;

public class ShellEditHandler extends AbstractHttpHandler{
	
	private StringShell shell;

	public ShellEditHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		if(shell==null || !shell.isAttiva()){
			shell = new StringShell();
		}
		
		String uri = exchange.getRequestURI().toString();
		int px= uri.indexOf("body=");
		String command = null;
		if(px>=0){
			command = URLDecoder.decode(uri.substring(px+"body=".length()),"ISO-8859-1");
		}
		
		if(command!=null){
			shell.writeCommand(command);
		}
		
		String content = shell.readPrompt();
		send(exchange, content, "html");
	}
	
	

}
