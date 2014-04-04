package it.bradipo.webdesktop.tree.handler;

import it.bradipo.webdesktop.Screen;
import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.http.IHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class TreePageHandler extends VelocityHandler {

	public TreePageHandler() {
		
	}
	
	
	@Override
	protected String getTemplate(IHttpRequest request) {
		return "tree.html";
	}
	
}
