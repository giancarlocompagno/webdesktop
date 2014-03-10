package it.bradipo.webdesktop;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.net.InetAddress;

import javax.management.RuntimeErrorException;

public class ServerData {
	
	private Rectangle screenRect;
	private String hostName;
	private Robot robot;
	private String sistemaOperativo;
	
	private static ServerData serverData;
	
	
	public static ServerData getInstance() {
		if(serverData==null){
			try{
				serverData = new ServerData();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		return serverData;
	}
	
	private ServerData() throws Exception {
		super();
		this.hostName = InetAddress.getLocalHost().getHostName();
		this.robot = new Robot();
		this.sistemaOperativo = System.getProperty("os.name");
		screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	
	public String getHostName() {
		return hostName;
	}
	
	public Robot getRobot() {
		return robot;
	}
	
	public String getSistemaOperativo() {
		return sistemaOperativo;
	}
	
	public Rectangle getScreenRect() {
		return screenRect;
	}

	
}