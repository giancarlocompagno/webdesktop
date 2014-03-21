package it.bradipo.webdesktop.so.windows;

import it.bradipo.webdesktop.task.Task;

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
