package it.bradipo.webdesktop.home.command;


import it.bradipo.webdesktop.Screen;

public class ClickCommand implements ICommand {
	
	public enum CLICKTYPE{MOUSEUP,MOUSEDOWN,RIGHTCLICK};
	
	private CLICKTYPE type;

	public ClickCommand(CLICKTYPE type) {
		this.type=type;
	}
	

	public void handle(String value) {
		int comma = value.indexOf(',');
        int x = value(value.substring(0, comma));
        int y = value(value.substring(comma + 1));
        Screen.mouseMove(x, y);
        switch (type) {
		case MOUSEDOWN:
			Screen.mouseDown();
			break;
		case MOUSEUP:
			Screen.mouseUp();
			break;
        case RIGHTCLICK:
        	Screen.rightClick();
			break;
        }
        
	}

	
	private static int value(String s){
		if(s.indexOf(".")>=0){
			s = s.substring(0,s.indexOf("."));
		}
		return Integer.parseInt(s);
	}
	
	

}
