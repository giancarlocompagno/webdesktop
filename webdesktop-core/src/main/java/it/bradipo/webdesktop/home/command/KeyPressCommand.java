package it.bradipo.webdesktop.home.command;

import it.bradipo.webdesktop.Screen;

import java.net.URLDecoder;

public class KeyPressCommand implements ICommand {
	
	public enum KEYTYPE{KEYPRESS,KEYDOWN,KEYUP};
	
	private KEYTYPE keytype;

	public KeyPressCommand(KEYTYPE keytype) {
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

	public void handle(String value) throws Exception {
		String javascriptKeyCodeS = URLDecoder.decode(value,"ISO-8859-1");
        Integer javascriptKeyCode = new Integer(javascriptKeyCodeS);
        Integer javaKeyCode = null;
        switch (keytype) {
		case KEYPRESS:
			Screen.key(Screen.keyJavaCode(javascriptKeyCode));
			break;
		case KEYDOWN:
			Screen.keyDown(Screen.keyDownUpJavaCode(javascriptKeyCode));
			break;
		case KEYUP:
			Screen.keyUp(Screen.keyDownUpJavaCode(javascriptKeyCode));
			break;	
		default:
			break;
		}
	}

}
