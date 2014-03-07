package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;

public class ShellEditHandler extends AbstractHttpHandler{
	
	private static final String EXIT_COMMAND = "exit"; 
	
	private Map<String,StringShell> shells = new HashMap<String,StringShell>();
	
	public ShellEditHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		HttpRequest request = new HttpRequest(exchange);
		
		String id = request.getParametro("id");
		if(id==null){
			id = "0";
		}
		
		
		StringShell shell = shells.get(id);
		
		if(shell==null || !shell.isAttiva()){
			shell = new StringShell("cmd.exe");
			shells.put(id, shell);
		}
		
		String command = request.getParametro("body");
		
		String content;
		
		if(command!=null){
			shell.writeCommand(command);
			content = shell.readPrompt();
		}else{		
			content = shell.readPrompt();
		}
		
		if(EXIT_COMMAND.equals(command)){
			shell.stop();
			content = content+"\n\n";
		}
		
		sendHTML(exchange, content);
	}
	
	

}
