package it.bradipo.webdesktop.handler.task;

import java.awt.Robot;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.AbstractHttpHandler.HttpRequest;
import it.bradipo.webdesktop.handler.task.windows.WindowsTaskManager;

public class TaskManagerPageHandler extends VelocityHandler {
	
	private TaskManager taskManager;

	public TaskManagerPageHandler(String hostName,String sistemaOperativo,Robot robot,boolean readOnly) {
		super(hostName,sistemaOperativo,robot,readOnly);
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		HttpRequest request = new HttpRequest(exchange);
		elaboraRichiesta(request);
		super.handle(exchange);
	}

	@Override
	protected Map<String, Object> getMap(HttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",getHostName());
		map.put("tasks",taskManager.getTasks());
		map.put("keys",taskManager.getKeys());
		return map;
	}

	private void elaboraRichiesta(HttpRequest request) {
		if(taskManager==null){
			taskManager = new WindowsTaskManager();
		}
		String pid  = request.getParametro("killprocess");
		if(pid!=null){
			taskManager.killTask(pid);
		}
		taskManager.reload();
	}
	
	@Override
	protected String getTemplate(HttpRequest request) {
		return "taskmanager.html";
	}

}
