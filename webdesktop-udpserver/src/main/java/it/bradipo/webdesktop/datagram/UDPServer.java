package it.bradipo.webdesktop.datagram;

import java.net.* ;
import java.io.*;
 
public class UDPServer extends Thread {
	
	public static int SIZE = 65536;
	
	protected DatagramSocket socket = null;
	
	private boolean start = true;
 
    public UDPServer(int port) throws IOException {
    	socket = new DatagramSocket(port);
    }
    
    public void run() {

    	while (start) {
    		try {
    			byte[] buf = new byte[SIZE];

    			// receive request
    			DatagramPacket packet = new DatagramPacket(buf, buf.length);
    			socket.receive(packet);
    			byte[] allpart = new byte[packet.getLength()];
    			System.arraycopy(buf, 0, allpart, 0, allpart.length);
    			StorageVideoOut.add(allpart);
    		} catch (IOException e) {
    			e.printStackTrace();
    			start = false;
    		}
    	}
    	socket.close();
    }
    
    public void shutdown(){
    	start = false;
    }
    
    
    public static void main(String[] args) throws IOException {
		new Thread(new UDPServer(4600)).start();
	}

}