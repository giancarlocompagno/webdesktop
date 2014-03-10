package it.bradipo.webdesktop.handler.shell;

import java.awt.Robot;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.http.IHttpRequest;

public class ShellPageHandler extends VelocityHandler {

	public ShellPageHandler() {
		
	}

	@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",ServerData.getInstance().getHostName());
		return map;
	}

	@Override
	protected String getTemplate(IHttpRequest request) {
		return "shell.html";
	}
}
