package it.bradipo.webdesktop.home.command;


import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ClickCommand extends AbstractCommand {
	
	public enum CLICKTYPE{MOUSEUP,MOUSEDOWN,RIGHTCLICK};
	
	private CLICKTYPE type;

	public ClickCommand(Robot robot,CLICKTYPE type) {
		super(robot);
		this.type=type;
	}
	

	public void handle(String value) {
		int comma = value.indexOf(',');
        int x = value(value.substring(0, comma));
        int y = value(value.substring(comma + 1));
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

	
	private static int value(String s){
		if(s.indexOf(".")>=0){
			s = s.substring(0,s.indexOf("."));
		}
		return Integer.parseInt(s);
	}
	
	

}
