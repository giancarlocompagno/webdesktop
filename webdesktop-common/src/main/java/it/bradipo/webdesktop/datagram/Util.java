package it.bradipo.webdesktop.datagram;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class Util {
	
	public static final String IMAGE_CODEC = "jpeg";
	
	public static final int UUID_LENGTH = 36;
	public static final int DEFAULT_ROW = 5;
	public static final int DEFAULT_COL = 5;
	
	public static void main(String[] args) {
		for(int i=0;i<Integer.MAX_VALUE;i++){
			if(i != fromByteArray(toByteArray(i))){
				System.out.println("ERRORE ");
				break;
			}
			if(i % 1000 == 0 ){
				System.out.println(i +" di " + Integer.MAX_VALUE);
			}
		}
		System.out.println("FINE");
	}
	
	public static byte[] toByteArray(int value) {
		return  ByteBuffer.allocate(4).putInt(value).array();
	}


	public static int fromByteArray(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] datagram(String uuid,int row,int col,byte[] part){
		byte[] p1 = uuid.toString().getBytes();
		byte[] p2 = toByteArray(row);
		byte[] p3 = toByteArray(col);
		//byte[] p4 = toByteArray(part.length);
		byte[] tot = new byte[p1.length+p2.length+p3.length/*+p4.length*/+part.length];
		System.arraycopy(p1,   0, tot, 0, p1.length);
		System.arraycopy(p2,   0, tot, p1.length, p2.length);
		System.arraycopy(p3,   0, tot, p1.length+p2.length, p3.length);
		//System.arraycopy(p4,   0, tot, p1.length+p2.length+p3.length, p4.length);
		System.arraycopy(part, 0, tot, p1.length+p2.length+p3.length/*+p4.length*/, part.length);
		return tot;
	}
	
	
	public static byte[] getImage(byte[][] bytes, int rows, int cols) throws IOException {
	    int chunks = rows * cols;  
  
        int chunkWidth, chunkHeight;  
        int type;  
        //fetching image files  
        /*File[] imgFiles = new File[chunks];  
        for (int i = 0; i < chunks; i++) {  
            imgFiles[i] = new File("archi" + i + ".jpg");  
        }*/ 
  
       //creating a bufferd image array from image files  
        BufferedImage[] buffImages = new BufferedImage[chunks];  
        for (int i = 0; i < chunks; i++) {  
            buffImages[i] = ImageIO.read(new ByteArrayInputStream(bytes[i]));  
        }  
        type = buffImages[0].getType();  
        chunkWidth = buffImages[0].getWidth();  
        chunkHeight = buffImages[0].getHeight();  
  
        //Initializing the final image  
        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);  
  
        int num = 0;  
        for (int i = 0; i < rows; i++) {  
            for (int j = 0; j < cols; j++) {  
                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);  
                num++;  
            }  
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
        ImageIO.write(finalImg, IMAGE_CODEC, bo);  
        return bo.toByteArray();
	} 

}
