package it.bradipo.webdesktop.home.handler;

import it.bradipo.webdesktop.Screen;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.home.command.ClickCommand;
import it.bradipo.webdesktop.home.command.KeyPressCommand;
import it.bradipo.webdesktop.home.command.ClickCommand.CLICKTYPE;
import it.bradipo.webdesktop.home.command.KeyPressCommand.KEYTYPE;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;


public class CommandHandler implements HttpHandler {
	
	private KeyPressCommand kp;
	private KeyPressCommand ku;
	private KeyPressCommand kd;
	private ClickCommand md;
	private ClickCommand mu;
	private ClickCommand rc;
	
	
	public CommandHandler(){
		kp=new KeyPressCommand(KEYTYPE.KEYPRESS);
		ku=new KeyPressCommand(KEYTYPE.KEYUP);
		kd=new KeyPressCommand(KEYTYPE.KEYDOWN);
		md=new ClickCommand(CLICKTYPE.MOUSEDOWN);
		mu=new ClickCommand(CLICKTYPE.MOUSEUP);
		rc=new ClickCommand(CLICKTYPE.RIGHTCLICK);
		
	}

	@Override
	public void handle(IHttpRequest httpRequest,IHttpResponse httpResponse) throws IOException {
		try{
			String command = httpRequest.getParametro("command");
			String[] comandi =command.split("_");
			for (int i = 0; i < comandi.length; i++) {
				String comando = comandi[i];
				if(!comando.isEmpty()){
					String key = comando.substring(0,2);
					String value = comando.substring(2);
					if("KP".equals(key)){
						kp.handle(value);
					}else if("KD".equals(key)){
						kd.handle(value);
					}else if("KU".equals(key)){
						ku.handle(value);
					}else if("MU".equals(key)){
						mu.handle(value);
					}else if("MD".equals(key)){
						md.handle(value);
					}else if("RC".equals(key)){
						rc.handle(value);
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		httpResponse.sendOK();
	}

}
