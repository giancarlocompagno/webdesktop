package it.bradipo.webdesktop.handler.home.command;

import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;

import java.awt.Robot;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class KeyPressCommand extends AbstractCommand {
	
	public enum KEYTYPE{KEYPRESS,KEYDOWN,KEYUP};
	
	private KEYTYPE keytype;

	public KeyPressCommand(Robot robot,KEYTYPE keytype) {
		super(robot);
		this.keytype=keytype;
	}

	/*@Override
	public void handle(HttpExchange exchange) throws IOException {		String path = exchange.getRequestURI().toASCIIString();
        int q = path.indexOf('?');
        int comma = path.indexOf('=', q);
        String value = path.substring(comma + 1);
        handle(value);
        sendOK(exchange);
	}*/

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
