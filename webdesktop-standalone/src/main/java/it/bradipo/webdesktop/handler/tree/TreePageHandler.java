package it.bradipo.webdesktop.handler.tree;

import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;

public class TreePageHandler extends VelocityHandler {

	public TreePageHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String ss = "";
		for(Integer x : CaratteriSpeciali.keydownupJavaKeyCodes()){
			ss = ss + "keydownup["+x+"] = false;\n"; 
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("readOnly",isReadOnly());
		
	    String content = template("tree.html", map);
	    send(exchange, content, "html");
	}

}
