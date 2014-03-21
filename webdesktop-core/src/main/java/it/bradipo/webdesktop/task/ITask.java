package it.bradipo.webdesktop.task;

import java.util.Collection;
import java.util.Set;

public interface ITask {

	public abstract void put(String key, String value);

	public abstract String get(String key);

	public abstract Set<String> keySet();
	
	public String getPID();
	
	public String getDescription();

	public Collection<String> value();

}