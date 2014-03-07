package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;
import it.bradipo.webdesktop.handler.AbstractHttpHandler.HttpRequest;
import it.bradipo.webdesktop.handler.home.command.ClickCommand;
import it.bradipo.webdesktop.handler.home.command.KeyPressCommand;
import it.bradipo.webdesktop.handler.home.command.ClickCommand.CLICKTYPE;
import it.bradipo.webdesktop.handler.home.command.KeyPressCommand.KEYTYPE;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class CommandHandler extends AbstractHttpHandler {
	
	private KeyPressCommand kp;
	private KeyPressCommand ku;
	private KeyPressCommand kd;
	private ClickCommand md;
	private ClickCommand mu;
	private ClickCommand rc;
	
	
	public CommandHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
		
		kp=new KeyPressCommand( robot,KEYTYPE.KEYPRESS);
		ku=new KeyPressCommand( robot, KEYTYPE.KEYUP);
		kd=new KeyPressCommand(robot, KEYTYPE.KEYDOWN);
		md=new ClickCommand( robot, CLICKTYPE.MOUSEDOWN);
		mu=new ClickCommand( robot, CLICKTYPE.MOUSEUP);
		rc=new ClickCommand(robot, CLICKTYPE.RIGHTCLICK);
		
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try{
			HttpRequest httpRequest = new HttpRequest(exchange);
			String subpath = httpRequest.getAllParams();
			String[] comandi =subpath.split("_");
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
		sendOK(exchange);
	}

}
