package it.bradipo.webdesktop.handler.task.unix;

import it.bradipo.webdesktop.handler.task.ITask;
import it.bradipo.webdesktop.handler.task.TaskManager;

public class UnixTaskManager extends TaskManager{

	@Override
	protected String killTaskCommand(ITask task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String listTaskCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ITask newTask(String[] keys,String line) {
		// TODO Auto-generated method stub
		return new UnixTask();
	}

	@Override
	protected String[] getKeys(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
