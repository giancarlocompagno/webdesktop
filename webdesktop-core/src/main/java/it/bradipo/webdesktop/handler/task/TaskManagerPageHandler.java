package it.bradipo.webdesktop.handler.task;

import it.bradipo.webdesktop.ServerData;
import it.bradipo.webdesktop.handler.VelocityHandler;
import it.bradipo.webdesktop.handler.so.SOFactory;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaskManagerPageHandler extends VelocityHandler {
	
	private TaskManager taskManager;

	public TaskManagerPageHandler() {
		super();
	}
	
	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		elaboraRichiesta(request);
		super.handle(request,response);
	}

	@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",ServerData.getInstance().getHostName());
		map.put("tasks",taskManager.getTasks());
		map.put("keys",taskManager.getKeys());
		return map;
	}

	private void elaboraRichiesta(IHttpRequest request) {
		if(taskManager==null){
			taskManager = SOFactory.getFactory().getTaskManager();
		}
		String pid  = request.getParametro("killprocess");
		if(pid!=null){
			taskManager.killTask(pid);
		}
		taskManager.reload();
	}
	
	@Override
	protected String getTemplate(IHttpRequest request) {
		return "taskmanager.html";
	}

}
