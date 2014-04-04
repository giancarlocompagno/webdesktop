package it.bradipo.webdesktop.servlet;

import it.bradipo.webdesktop.datagram.UDPServer;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.servlet.handler.ProxyHandler;
import it.bradipo.webdesktop.util.HandlerManager;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server extends HttpServlet{
	
	
	Registry r = new Registry();
	UDPServer server;
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		String datagramPort = servletConfig.getInitParameter("datagramPort");
		r.setDefault(new ProxyHandler(HandlerManager.getDefault()));
		try {
			r.put("/screen", new ProxyHandler(datagramPort!=null?HandlerManager.getDatagram():HandlerManager.getStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Entry<String, HttpHandler> entry : HandlerManager.getHttpHandlers()){
			r.put(entry.getKey(),new ProxyHandler(entry.getValue()));
		}
		
		if(datagramPort!=null){
			try {
				server = new UDPServer(Integer.parseInt(datagramPort));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException(e);
			}
			server.start();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
		if(server!=null){
			server.shutdown();
		}
	}
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		execute(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		execute(request, response);
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String uri = request.getPathInfo();
		
		ProxyHandler proxy = r.get(uri);
		if(proxy!=null){
			proxy.handle(request, response);
		}else{
			r.getDefault().handle(request, response);
		}
		
		
	}
	
	
	
	

}
