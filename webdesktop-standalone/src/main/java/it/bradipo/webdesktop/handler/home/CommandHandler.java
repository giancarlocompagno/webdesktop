package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;
import it.bradipo.webdesktop.handler.home.ClickHandler.CLICKTYPE;
import it.bradipo.webdesktop.handler.home.KeyPressHandler.KEYTYPE;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class CommandHandler extends AbstractHttpHandler {
	
	private KeyPressHandler kp;
	private KeyPressHandler ku;
	private KeyPressHandler kd;
	private ClickHandler md;
	private ClickHandler mu;
	private ClickHandler rc;
	
	
	public CommandHandler(String hostName,Robot robot,boolean readOnly) {
		super(hostName,robot,readOnly);
		
		kp=new KeyPressHandler(hostName, robot,isReadOnly(), KEYTYPE.KEYPRESS);
		ku=new KeyPressHandler(hostName, robot,isReadOnly(), KEYTYPE.KEYUP);
		kd=new KeyPressHandler(hostName, robot,isReadOnly(), KEYTYPE.KEYDOWN);
		md=new ClickHandler(hostName, robot,isReadOnly(), CLICKTYPE.MOUSEDOWN);
		mu=new ClickHandler(hostName, robot,isReadOnly(), CLICKTYPE.MOUSEUP);
		rc=new ClickHandler(hostName, robot,isReadOnly(), CLICKTYPE.RIGHTCLICK);
		
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try{
			String path = exchange.getRequestURI().toASCIIString();
			String subpath = getSubpath(path);
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
