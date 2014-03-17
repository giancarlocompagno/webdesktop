package it.bradipo.webdesktop.handler.so;

import it.bradipo.webdesktop.handler.shell.StringShell;
import it.bradipo.webdesktop.handler.task.TaskManager;

public interface Factory {
	
	public StringShell getShell();
	public TaskManager getTaskManager();

}
