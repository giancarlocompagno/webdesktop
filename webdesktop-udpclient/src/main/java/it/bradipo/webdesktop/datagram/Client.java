package it.bradipo.webdesktop.datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.UUID;

import static it.bradipo.webdesktop.datagram.Util.*;

public class Client implements Runnable{
	
	DatagramSocket dsocket; //socket to be used to send and receive UDP packets
	DatagramPacket senddp; //UDP packet containing the video frames
	VideoIn imageIn;
	InetAddress address;
	int port;
	
	public Client(int rows,int cols,InetAddress address,int port) throws SocketException {
		imageIn=new VideoIn(rows, cols);
		this.address=address;
		this.port=port;
		dsocket = new DatagramSocket();
	}
	
	public UUID getUuid(){
		return imageIn.getUuid();
	}
	
	public void run() {
		while(true){
			try{
				byte[][] next = imageIn.getNextImage();
				for(int i=0;i<next.length;i++){
					senddp = new DatagramPacket(next[i], next[i].length,address,port);
					dsocket.send(senddp);
				}
				Thread.sleep(200);
			}catch(Exception e){
				e.printStackTrace();
				break;
			}
		}
		
	}

	
	
	public static void main(String[] args) throws Exception {
		new Thread(new Client(DEFAULT_ROW, DEFAULT_COL, InetAddress.getByName("localhost"), 4600)).start();
	}
}
