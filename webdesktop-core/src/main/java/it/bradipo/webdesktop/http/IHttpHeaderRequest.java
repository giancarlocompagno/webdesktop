package it.bradipo.webdesktop.http;

import java.util.Collection;
import java.util.List;

public interface IHttpHeaderRequest {
	
	public List<String> get(String content);
	
	public String getFirst(String content);
	
	public Collection<String> keys();
	

}
