package it.bradipo.webdesktop.shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShellManager {
	
	private static Map<String,StringShell> shells = new HashMap<String,StringShell>();
	
	public static StringShell get(String id) {
		return shells.get(id);
	}

	public static void put(String id, StringShell shell) {
		shells.put(id, shell);
	}
	
	public static Set<String> ids(){
		return shells.keySet();
	}
	

}
