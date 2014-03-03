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
	
	private Map<String,ITask> tasks = new LinkedHashMap<String,ITask>();
	
	
	public TaskManager() {
		reload();
	}
	
	//taskkill /F /PDA <pid>
	protected abstract String killTaskCommand(ITask task);
	
	//String taskListCommand = "tasklist /v /fo csv";
	protected abstract String listTaskCommand();
	
	protected abstract ITask newTask(String[] keys,String line);

	public void killTask(String pid) {
		ITask task = tasks.get(pid);
		if(task!=null){
			try {
				Process p = Runtime.getRuntime().exec(killTaskCommand(task));
			} catch (IOException e) {
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
			String[]  keys = null;
			while((line = input.readLine()) != null){
				if (!line.trim().equals("")) {
					keys = getKeys(line);
					break;
				}
			}
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					ITask task = newTask(keys,line);
					tasks.put(task.getPID(), task);
				}
			}
			input.close();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	protected abstract String[] getKeys(String line);
	
}
