package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;

public class ShellPageHandler extends VelocityHandler {

	public ShellPageHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		
	    String content = template("shell.html", map);
	    send(exchange, content, "html");
	}

}
