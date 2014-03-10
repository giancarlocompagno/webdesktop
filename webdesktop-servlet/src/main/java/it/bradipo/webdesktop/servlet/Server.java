package it.bradipo.webdesktop.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		execute(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		execute(request, response);
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response){
		String uri = request.getRequestURI();
		
		
	}
	

}
