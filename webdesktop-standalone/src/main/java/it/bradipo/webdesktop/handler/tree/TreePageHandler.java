package it.bradipo.webdesktop.handler.tree;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;

public class TreePageHandler extends VelocityHandler {

	public TreePageHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
	}
	
	
	@Override
	protected String getTemplate(HttpRequest request) {
		return "tree.html";
	}
	
	@Override
	protected Map<String, Object> getMap(HttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		return map;
	}
	
}
