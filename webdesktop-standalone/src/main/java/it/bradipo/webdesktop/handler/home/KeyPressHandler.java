package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;
import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;

import java.awt.Robot;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

public class KeyPressHandler extends AbstractHttpHandler {
	
	public enum KEYTYPE{KEYPRESS,KEYDOWN,KEYUP};
	
	private KEYTYPE keytype;

	public KeyPressHandler(String hostName,Robot robot,boolean readOnly,KEYTYPE keytype) {
		super(hostName,robot,readOnly);
		this.keytype=keytype;
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {		String path = exchange.getRequestURI().toASCIIString();
        int q = path.indexOf('?');
        int comma = path.indexOf('=', q);
        String value = path.substring(comma + 1);
        handle(value);
        send(exchange, "ok");
	}

	public void handle(String value) throws UnsupportedEncodingException {
		String javascriptKeyCodeS = URLDecoder.decode(value,"ISO-8859-1");
        Integer javascriptKeyCode = new Integer(javascriptKeyCodeS);
        Integer javaKeyCode = null;
        switch (keytype) {
		case KEYPRESS:
			javaKeyCode = CaratteriSpeciali.keypressJavaCode(javascriptKeyCode);
			getRobot().keyPress(javaKeyCode);
			getRobot().keyRelease(javaKeyCode);
			break;
		case KEYDOWN:
			javaKeyCode = CaratteriSpeciali.keydownupJavaCode(javascriptKeyCode);
			getRobot().keyPress(javaKeyCode);
			break;
		case KEYUP:
			javaKeyCode = CaratteriSpeciali.keydownupJavaCode(javascriptKeyCode);
			getRobot().keyRelease(javaKeyCode);
			break;	
		default:
			break;
		}
	}

}
