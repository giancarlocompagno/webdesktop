package it.bradipo.webdesktop.home.command;

import java.awt.Robot;

public class AbstractCommand {
	
	private Robot robot;
	
	public AbstractCommand(Robot robot){
		this.robot=robot;
	}
	
	public Robot getRobot() {
		return robot;
	}

}
