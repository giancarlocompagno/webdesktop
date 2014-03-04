package it.bradipo.webdesktop.handler.task;




/** 
 * Sends CTRL-C to running processes from Java (in Windows)
 * and ca get ProcessID(s) for a given process name.
 * IMPORTANT!
 * This function NEEDS SendSignalC.exe in the ext subdirectory.
 * @author Kai Goergen
 */

import java.io.*;
import java.util.*;

public abstract class TaskManager {
	
	protected Map<String,ITask> tasks = new LinkedHashMap<String,ITask>();
	protected List<String> keys = new ArrayList<String>();
	
	public TaskManager() {
		reload();
	}
	
	//taskkill /F /PDA <pid>
	protected abstract String killTaskCommand(ITask task);
	
	//String taskListCommand = "tasklist /v /fo csv";
	protected abstract String listTaskCommand();
	
	protected abstract ITask newTask(String line);

	public void killTask(String pid) {
		ITask task = tasks.get(pid);
		if(task!=null){
			try {
				Process p = Runtime.getRuntime().exec(killTaskCommand(task));
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public Collection<ITask> getTasks() {
		return tasks.values();
	}
	
	/**
	 * Get List of PIDs for a given process name
	 * @param processName
	 * @return
	 */
	public void reload(){
		try {
			String line;
			
			Process p = Runtime.getRuntime().exec(listTaskCommand());
			BufferedReader input = new BufferedReader
					(new InputStreamReader(p.getInputStream()));
			while((line = input.readLine()) != null){
				if (!line.trim().equals("")) {
					defineKeys(line);
					break;
				}
			}
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					ITask task = newTask(line);
					tasks.put(task.getPID(), task);
				}
			}
			input.close();
			p.waitFor();
			p.destroy();
		}catch (Exception err) {
			err.printStackTrace();
		}
	}

	protected abstract void defineKeys(String line);

	public List<String> getKeys() {
		return this.keys;
	}
	
}
