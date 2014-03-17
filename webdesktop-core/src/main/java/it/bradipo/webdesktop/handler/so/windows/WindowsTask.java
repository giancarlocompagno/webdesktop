package it.bradipo.webdesktop.handler.so.windows;

import it.bradipo.webdesktop.handler.task.Task;

class WindowsTask extends Task{

	@Override
	public String getPID() {
		return get("PID");
	}

	@Override
	public String getDescription() {
		return toString();
	}
	
}
