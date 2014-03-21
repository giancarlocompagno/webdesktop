package it.bradipo.webdesktop.so;

import it.bradipo.webdesktop.shell.StringShell;
import it.bradipo.webdesktop.task.TaskManager;

public interface Factory {
	
	public StringShell getShell();
	public TaskManager getTaskManager();

}
