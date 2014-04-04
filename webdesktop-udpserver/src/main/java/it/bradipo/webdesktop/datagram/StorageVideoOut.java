package it.bradipo.webdesktop.datagram;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static it.bradipo.webdesktop.datagram.Util.*;

public class StorageVideoOut {
	
	private static Map<String,VideoOut> maps = new HashMap<String, VideoOut>();
	
	
	private StorageVideoOut() {}
	
	public static void start(String uuid,int rows,int cols){
		maps.put(uuid, new VideoOut(uuid, rows, cols));
	}
	
	public static void stop(String uuid){
		maps.remove(uuid);
	}
	
	
	public static void add(byte[] allpart){
		byte[] uuidB = new byte[UUID_LENGTH];
		System.arraycopy(allpart, 0, uuidB, 0, UUID_LENGTH);
		String uuid = new String(uuidB);
		byte[] part = new byte[allpart.length - UUID_LENGTH];
		System.arraycopy(allpart, UUID_LENGTH, part, 0, part.length);
		if(maps.get(uuid)==null){
			start(uuid, DEFAULT_ROW, DEFAULT_COL);
		}
		maps.get(uuid).setPart(part);
	}
	
	
	public static byte[] getNextImage(String uuid) throws IOException{
		return maps.get(uuid).getImage();
	}
	
	public static boolean exist(String uuid){
		return maps.containsKey(uuid);
	}

}
