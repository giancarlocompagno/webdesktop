package it.bradipo.webdesktop.shell.handler;

import java.util.Map;

import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.shell.ShellManager;
import it.bradipo.webdesktop.task.handler.TaskManagerPageHandler;

public class ShellPageHandler extends TaskManagerPageHandler {

	public ShellPageHandler() {
		
	}

	@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		Map<String, Object> map = super.getMap(request);
		for(String id : ShellManager.ids()){
			map.put("shell_"+id, ShellManager.get(id).readAll());
		}
		return map;
	}

	@Override
	protected String getTemplate(IHttpRequest request) {
		return "shell.html";
	}
}
