package it.bradipo.webdesktop.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface IHttpRequest {
	
	public String getUri();

	public String getUrl();

	public String getAllParams();
	
	public InputStream getInputStream() throws IOException; 

	public String getParametro(String parametro);

	public Collection<String> getKeys();

	public Collection<String> getValues();
	
	public IHttpHeaderRequest getHttpHeaderRequest();

	public String getContextPath();

}