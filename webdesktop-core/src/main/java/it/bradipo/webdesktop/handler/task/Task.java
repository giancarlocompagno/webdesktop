package it.bradipo.webdesktop.handler.task;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class Task implements ITask {
	
	private Map<String,String> task = new LinkedHashMap<String,String>();
	
	public Task() {}
	
	/* (non-Javadoc)
	 * @see it.bradipo.webdesktop.handler.shell.ITask#put(java.lang.String, java.lang.String)
	 */
	@Override
	public void put(String key,String value){
		task.put(key, value);
	}
	
	/* (non-Javadoc)
	 * @see it.bradipo.webdesktop.handler.shell.ITask#get(java.lang.String)
	 */
	@Override
	public String get(String key){
		return task.get(key);
	}
	
	/* (non-Javadoc)
	 * @see it.bradipo.webdesktop.handler.shell.ITask#keySet()
	 */
	@Override
	public Set<String> keySet(){
		return task.keySet();
	}
	
	@Override
	public Collection<String> value(){
		return task.values();
	}

	@Override
	public String toString() {
		return this.getClass()+task.toString();
	}
	
	
	
	
}

