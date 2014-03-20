package it.bradipo.webdesktop.handler.shell;

import it.bradipo.webdesktop.handler.task.TaskManagerPageHandler;
import it.bradipo.webdesktop.http.IHttpRequest;

public class ShellPageHandler extends TaskManagerPageHandler {

	public ShellPageHandler() {
		
	}

	/*@Override
	protected Map<String, Object> getMap(IHttpRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostName",ServerData.getInstance().getHostName());
		return map;
	}*/

	@Override
	protected String getTemplate(IHttpRequest request) {
		return "shell.html";
	}
}
