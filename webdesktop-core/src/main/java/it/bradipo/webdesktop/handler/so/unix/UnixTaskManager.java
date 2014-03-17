package it.bradipo.webdesktop.handler.so.unix;

import it.bradipo.webdesktop.handler.task.ITask;
import it.bradipo.webdesktop.handler.task.TaskManager;

class UnixTaskManager extends TaskManager{

	@Override
	protected String killTaskCommand(ITask task) {
		return "kill -9 "+task.getPID();
	}

	@Override
	protected String listTaskCommand() {
		return "ps -ef";
	}
	@Override
	protected ITask newTask(String line) {
		ITask task = new UnixTask();
		String[] values = line.split("\t");
		for(int i=0;i<values.length;i++){
			task.put(keys.get(i), values[i].substring(1,values[i].length()-1));
		}
		return task;
	}
	
	@Override
	protected void defineKeys(String line) {
		this.keys.clear();
		String[] keys = line.split("\t");
		for(int i=0;i<keys.length;i++){
			this.keys.add(keys[i].substring(1,keys[i].length()-1));
		}
	}

}
