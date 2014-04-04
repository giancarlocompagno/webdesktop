package it.bradipo.webdesktop.datagram;

import java.io.FileOutputStream;
import java.io.IOException;

import static it.bradipo.webdesktop.datagram.Util.*;

public class VideoOut{
	
	private String uuid;
	
	private int rows;
	private int cols;
	private  byte[][] parts;
	
	private boolean ready = false;
	
	public VideoOut(String uuid,int rows,int cols) {
		this.uuid=uuid;
		this.rows=rows;
		this.cols=cols;
		this.parts=new byte[rows*cols][];
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public synchronized void setPart(byte[] allpart){
		byte[] rowB = new byte[4];
		System.arraycopy(allpart, 0, rowB, 0, 4);
		byte[] colB = new byte[4];
		System.arraycopy(allpart, 4, colB, 0, 4);
		byte[] lengthB = new byte[4];
		System.arraycopy(allpart, 8, lengthB, 0, 4);
		//int length = fromByteArray(lengthB);
		byte[] part = new byte[allpart.length-8];
		System.arraycopy(allpart, 8, part, 0, part.length);
		int row = fromByteArray(rowB);
		int col = fromByteArray(colB);
		this.parts[row*this.cols+col]=part;
		if(!ready){
			ready=true;
			for(int i=0;i<this.parts.length;i++){
				if(this.parts[i]==null){
					ready=false;
					break;
				}
			}
			/*if(ready){
				notifyAll();
			}*/
		}
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public synchronized byte[] getImage() throws IOException{
//		if(!ready){
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		return Util.getImage(parts, rows, cols);
	}
	
	

}
