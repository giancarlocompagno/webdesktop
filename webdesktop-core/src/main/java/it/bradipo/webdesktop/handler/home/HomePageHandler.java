package it.bradipo.webdesktop.handler.home;

import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.util.CaratteriSpeciali;
import it.bradipo.webdesktop.http.IHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class HomePageHandler extends VelocityHandler {

	public HomePageHandler() {
		super();
	}
	@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		String ss = "";
		for(Integer x : CaratteriSpeciali.keydownupJavaKeyCodes()){
			ss = ss + "keydownup["+x+"] = false;\n"; 
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",ServerData.getInstance().getHostName());
		map.put("extenalScript",ss);
	    return map;
	}
	
	@Override
	protected String getTemplate(IHttpRequest request) {
		return "home.html";
	}

}
