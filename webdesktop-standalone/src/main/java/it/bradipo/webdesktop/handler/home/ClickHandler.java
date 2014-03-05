package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class ClickHandler extends AbstractHttpHandler {
	
	public enum CLICKTYPE{MOUSEUP,MOUSEDOWN,RIGHTCLICK};
	
	private CLICKTYPE type;

	public ClickHandler(String hostName,Robot robot,boolean readOnly,CLICKTYPE type) {
		super(hostName,robot,readOnly);
		this.type=type;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String path = exchange.getRequestURI().toASCIIString();
        String subpath = getSubpath(path);
        handle(subpath);
        sendOK(exchange);
	}

	

	public void handle(String value) {
		int comma = value.indexOf(',');
        int x = Integer.parseInt(value.substring(0, comma));
        int y = Integer.parseInt(value.substring(comma + 1));
        getRobot().mouseMove(x, y);
        switch (type) {
		case MOUSEDOWN:
			getRobot().mousePress(InputEvent.BUTTON1_MASK);
			break;
		case MOUSEUP:
			getRobot().mouseRelease(InputEvent.BUTTON1_MASK);
			break;
        case RIGHTCLICK:
        	getRobot().keyPress(KeyEvent.VK_CONTEXT_MENU);
			getRobot().keyRelease(KeyEvent.VK_CONTEXT_MENU);
			break;
        }
        
	}

}
