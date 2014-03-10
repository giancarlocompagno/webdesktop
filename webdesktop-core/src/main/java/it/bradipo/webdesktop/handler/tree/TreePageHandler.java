package it.bradipo.webdesktop.handler.tree;

import java.util.HashMap;
import java.util.Map;


import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.http.IHttpRequest;

public class TreePageHandler extends VelocityHandler {

	public TreePageHandler() {
		
	}
	
	
	@Override
	protected String getTemplate(IHttpRequest request) {
		return "tree.html";
	}
	
	@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",ServerData.getInstance().getHostName());
		return map;
	}
	
}
