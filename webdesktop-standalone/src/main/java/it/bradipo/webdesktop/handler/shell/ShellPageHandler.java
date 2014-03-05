package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;

public class ShellPageHandler extends VelocityHandler {

	public ShellPageHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Map<String, Object> getMap(HttpExchange exchange) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		return map;
	}

	@Override
	protected String getTemplate(HttpExchange exchange) {
		return "shell.html";
	}
}
