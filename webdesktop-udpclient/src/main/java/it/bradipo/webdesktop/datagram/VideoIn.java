package it.bradipo.webdesktop.datagram;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;




import it.bradipo.webdesktop.Screen;
import static it.bradipo.webdesktop.datagram.Util.*;

public class VideoIn{
	
	UUID uuid;
	
	private int rows;
	private int cols;
	
	public VideoIn(String uuid,int rows,int cols) {
		this.rows=rows;
		this.cols=cols;
		this.uuid=UUID.fromString(uuid);
		System.out.println(this.uuid.toString());
	}
	
	public VideoIn(int rows,int cols) {
		this.rows=rows;
		this.cols=cols;
		this.uuid=UUID.randomUUID();
		System.out.println(this.uuid.toString());
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public byte[][] getNextImage() throws IOException{
		byte[][] curr = Screen.getScreen(rows, cols);
		//if(false){
		for(int row=0;row<rows;row++){
			for(int col=0;col<cols;col++){
				curr[row*cols+col] = datagram(uuid.toString(), row, col, curr[row*cols+col]);
			}
		}
		//}
		return curr;
	}
	
	
	public static void main(String[] args) throws Exception {
		byte[][] f1 = new VideoIn("627c0adf-9464-4ed3-9e87-68870ab4a4c3",5, 5).getNextImage();
		for(int i=0;i<f1.length;i++){
			FileOutputStream f = new FileOutputStream("C:\\Documents and Settings\\Giancarlo\\Desktop\\img\\img1_"+i+".jpg");
			f.write(f1[i]);
			f.flush();
			f.close();
		}
	}
	

	


	

}
