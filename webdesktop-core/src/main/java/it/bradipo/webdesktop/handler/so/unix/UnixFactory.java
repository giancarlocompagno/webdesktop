package it.bradipo.webdesktop.handler.so.unix;

import java.io.IOException;

import it.bradipo.webdesktop.handler.shell.StringShell;
import it.bradipo.webdesktop.handler.so.Factory;
import it.bradipo.webdesktop.handler.task.Task;
import it.bradipo.webdesktop.handler.task.TaskManager;

public class UnixFactory implements Factory{

	@Override
	public StringShell getShell() {
		try{
			return new UnixShell();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public TaskManager getTaskManager() {
		return new UnixTaskManager();
	}

}
