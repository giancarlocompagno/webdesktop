package it.bradipo.webdesktop.handler;

import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public abstract class VelocityHandler  implements HttpHandler{
		
	VelocityEngine ve = new VelocityEngine();
	
	public VelocityHandler() {
		ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
		ve.init();
	}

	public String template(String template, Map<String,?> map) {
		
        /*  next, get the Template  */
        Template t = ve.getTemplate( "/vm/"+template );
        VelocityContext context = new VelocityContext();
        for(java.util.Map.Entry<String,?> curr : map.entrySet()){
        	context.put(curr.getKey(),curr.getValue());
        }
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        return writer.toString();     
	}
	
	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		Map<String,Object> map = getMap(request);
		map.put("CONTEXT_PATH", request.getContextPath());
		String content = template(getTemplate(request), map);
	    response.sendHTML(content);
	}

	protected abstract String getTemplate(IHttpRequest request) ;

	protected abstract Map<String, Object> getMap(IHttpRequest request);
	
	

}
