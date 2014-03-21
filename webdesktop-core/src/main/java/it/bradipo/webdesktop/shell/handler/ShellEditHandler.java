package it.bradipo.webdesktop.shell.handler;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;
import it.bradipo.webdesktop.shell.ShellManager;
import it.bradipo.webdesktop.shell.StringShell;
import it.bradipo.webdesktop.so.SOFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShellEditHandler implements HttpHandler{
	
	private static final String EXIT_COMMAND = "exit"; 
	
	
	
	public ShellEditHandler() {
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		String id = request.getParametro("id");
		if(id==null){
			id = "0";
		}
		
		
		StringShell shell = ShellManager.get(id);
		
		if(shell==null || !shell.isAttiva()){
			shell = SOFactory.getFactory().getShell();
			ShellManager.put(id, shell);
		}
		
		String command = request.getParametro("body");
		
		String content;
		
		if(command!=null){
			shell.writeCommand(command);
		}content = shell.readPrompt();
		
		if(EXIT_COMMAND.equals(command)){
			shell.stop();
			content = content+"\n\n";
		}
		
		response.sendHTML(content);
	}
	
	

}
