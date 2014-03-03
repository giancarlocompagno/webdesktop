package it.bradipo.webdesktop.handler.task;

import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.task.windows.WindowsTaskManager;

public class TaskManagerPageHandler extends VelocityHandler {
	
	private TaskManager taskManager;

	public TaskManagerPageHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		if(taskManager==null){
			taskManager = new WindowsTaskManager();
		}
		String url = exchange.getRequestURI().toString();
		int indexOf = url.indexOf("?killprocess=");
		if(indexOf>=0){
			String pid = url.substring(indexOf+"?killprocess=".length());
			taskManager.killTask(pid);
		}
		
		taskManager.reload();
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("tasks",taskManager.getTasks());
		
	    String content = template("taskmanager.html", map);
	    send(exchange, content, "html");

	}

}
