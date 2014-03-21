package it.bradipo.webdesktop.so.windows;

import it.bradipo.webdesktop.shell.StringShell;
import it.bradipo.webdesktop.so.Factory;
import it.bradipo.webdesktop.task.TaskManager;

import java.io.IOException;

public class WindowsFactory implements Factory{

	@Override
	public StringShell getShell(){
		try{
			return new WindowsShell();
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public TaskManager getTaskManager() {
		return new WindowsTaskManager();
	}

}
