package it.bradipo.webdesktop.http;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IHttpHeaderRequest {
	
	public List<String> get(String content);
	
	public String getFirst(String content);
	
	public Collection<String> keys();
	

}
