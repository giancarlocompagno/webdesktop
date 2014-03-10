package it.bradipo.webdesktop.handler.task.windows;

import it.bradipo.webdesktop.handler.task.Task;

public class WindowsTask extends Task{

	@Override
	public String getPID() {
		return get("PID");
	}

	@Override
	public String getDescription() {
		return toString();
	}
	
}
