package it.bradipo.webdesktop.handler.tree;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import it.bradipo.webdesktop.handler.VelocityHandler;

public class TreePageHandler extends VelocityHandler {

	public TreePageHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected String getTemplate(HttpExchange exchange) {
		return "tree.html";
	}
	
	@Override
	protected Map<String, Object> getMap(HttpExchange exchange) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		return map;
	}
	
}
