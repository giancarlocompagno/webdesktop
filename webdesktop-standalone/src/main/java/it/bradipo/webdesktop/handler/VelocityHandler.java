package it.bradipo.webdesktop.handler;

import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.sun.net.httpserver.HttpExchange;

public abstract class VelocityHandler  extends AbstractHttpHandler{
		
	VelocityEngine ve = new VelocityEngine();
	
	public VelocityHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, new File(VelocityHandler.class.getResource("/vm/" ).getFile()).getAbsolutePath());
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
		ve.init();
	}

	public String template(String template, Map<String,?> map) {

        /*  next, get the Template  */
        Template t = ve.getTemplate( template );
        VelocityContext context = new VelocityContext();
        for(java.util.Map.Entry<String,?> curr : map.entrySet()){
        	context.put(curr.getKey(),curr.getValue());
        }
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        return writer.toString();     
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String,Object> map = getMap(exchange);
		String content = template(getTemplate(exchange), map);
	    sendHTML(exchange, content);
	}

	protected abstract String getTemplate(HttpExchange exchange) ;

	protected abstract Map<String, Object> getMap(HttpExchange exchange);
	
	

}
