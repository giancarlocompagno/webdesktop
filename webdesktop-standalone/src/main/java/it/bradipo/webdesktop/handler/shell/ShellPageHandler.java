package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.AbstractHttpHandler.HttpRequest;

public class ShellPageHandler extends VelocityHandler {

	public ShellPageHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
	}

	@Override
	protected Map<String, Object> getMap(HttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		return map;
	}

	@Override
	protected String getTemplate(HttpRequest request) {
		return "shell.html";
	}
}
