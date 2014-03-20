package it.bradipo.webdesktop.servlet;

import it.bradipo.webdesktop.servlet.handler.ProxyHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Registry {
	
	private ProxyHandler defualt;
	private Map<String,ProxyHandler> map = new HashMap<String, ProxyHandler>();
	
	

	public void put(String key, ProxyHandler proxyHandler) {
		map.put(key, proxyHandler);
	}

	public ProxyHandler get(String uri) {
		for(Entry<String, ProxyHandler> e : map.entrySet()){
			if(uri.equals(e.getKey())||uri.startsWith(e.getKey()+"/")){
				return e.getValue();
			}
		}
		return null;
	}

	public ProxyHandler getDefault() {
		return defualt;
	}

	public void setDefault(ProxyHandler defualt) {
		this.defualt=defualt;
		
	}

}
