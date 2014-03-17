package it.bradipo.webdesktop.handler.shell;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;





import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.so.SOFactory;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

public class ShellEditHandler implements HttpHandler{
	
	private static final String EXIT_COMMAND = "exit"; 
	
	private Map<String,StringShell> shells = new HashMap<String,StringShell>();
	
	public ShellEditHandler() {
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		String id = request.getParametro("id");
		if(id==null){
			id = "0";
		}
		
		
		StringShell shell = shells.get(id);
		
		if(shell==null || !shell.isAttiva()){
			shell = SOFactory.getFactory().getShell();
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
		
		response.sendHTML(content);
	}
	
	

}
