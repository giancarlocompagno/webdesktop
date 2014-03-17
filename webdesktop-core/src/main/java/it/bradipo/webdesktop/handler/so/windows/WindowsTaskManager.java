package it.bradipo.webdesktop.handler.so.windows;

import it.bradipo.webdesktop.handler.task.ITask;
import it.bradipo.webdesktop.handler.task.TaskManager;

class WindowsTaskManager extends TaskManager {
	
	public WindowsTaskManager() {
		
	}

	@Override
	protected String killTaskCommand(ITask task) {
		return "taskkill /F /V /PID "+task.getPID();
	}

	@Override
	protected String listTaskCommand() {
		return "tasklist /v /fo csv";
	}

	@Override
	protected ITask newTask(String line) {
		ITask task = new WindowsTask();
		String[] values = line.split(",");
		for(int i=0;i<values.length;i++){
			task.put(keys.get(i), values[i].substring(1,values[i].length()-1));
		}
		return task;
	}
	
	@Override
	protected void defineKeys(String line) {
		this.keys.clear();
		String[] keys = line.split(",");
		for(int i=0;i<keys.length;i++){
			this.keys.add(keys[i].substring(1,keys[i].length()-1));
		}
	}

}
