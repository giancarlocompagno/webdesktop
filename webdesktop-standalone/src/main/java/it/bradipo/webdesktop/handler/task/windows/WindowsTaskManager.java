package it.bradipo.webdesktop.handler.task.windows;

import it.bradipo.webdesktop.handler.task.ITask;
import it.bradipo.webdesktop.handler.task.TaskManager;

public class WindowsTaskManager extends TaskManager {

	@Override
	protected String killTaskCommand(ITask task) {
		return "taskkill /F /PID "+task.getPID();
	}

	@Override
	protected String listTaskCommand() {
		return "tasklist /v /fo csv";
	}

	@Override
	protected ITask newTask(String[] keys,String line) {
		ITask task = new WindowsTask();
		String[] values = line.split(",");
		for(int i=0;i<values.length;i++){
			task.put(keys[i], values[i].substring(1,values[i].length()-1));
		}
		return task;
	}
	
	@Override
	protected String[] getKeys(String line) {
		String[] keys = line.split(",");
		for(int i=0;i<keys.length;i++){
			keys[i] = keys[i].substring(1,keys[i].length()-1);
		}
		return keys;	
	}

}
