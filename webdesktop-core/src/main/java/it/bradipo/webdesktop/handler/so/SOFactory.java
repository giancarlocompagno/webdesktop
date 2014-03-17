package it.bradipo.webdesktop.handler.so;

import it.bradipo.webdesktop.handler.so.unix.UnixFactory;
import it.bradipo.webdesktop.handler.so.windows.WindowsFactory;

public class SOFactory {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	private static Factory factory;
	
	static{
		if (isWindows()) {
			factory = new WindowsFactory();
		} else if (isMac()) {
			factory = new UnixFactory();
		} else if (isUnix()) {
			factory = new UnixFactory();
		} else if (isSolaris()) {
			factory = new UnixFactory();
		} else {
			throw new RuntimeException("OS: "+OS+" not supported");
		}
	}
	
	public static Factory getFactory() {
		return factory;
	}
	
	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
 
	private static boolean isMac() {
 		return (OS.indexOf("mac") >= 0);
 	}
 
	private static boolean isUnix() {
 		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 	}
 
	private static boolean isSolaris() {
 		return (OS.indexOf("sunos") >= 0);
 	}
	
}
