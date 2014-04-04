package it.bradipo.webdesktop;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Screen {
	

	
	
private static Map<Integer,Integer> keydownup = new HashMap<Integer, Integer>();
	
	private static Map<Integer,Integer> keypress = new HashMap<Integer, Integer>();
	static{
		
		keydownup.put(16,KeyEvent.VK_SHIFT);
		keydownup.put(17,KeyEvent.VK_CONTROL);
		keydownup.put(18,KeyEvent.VK_ALT);
		
		keypress.put(524, KeyEvent.VK_WINDOWS);
		keypress.put(525, KeyEvent.VK_CONTEXT_MENU);
		
		keypress.put(8,KeyEvent.VK_BACK_SPACE);
		keypress.put(13,KeyEvent.VK_ENTER);
		keypress.put(9,KeyEvent.VK_TAB);
		keypress.put(19,KeyEvent.VK_PAUSE);
		keypress.put(20,KeyEvent.VK_CAPS_LOCK);
		keypress.put(27,KeyEvent.VK_ESCAPE);
		keypress.put(32,KeyEvent.VK_SPACE);
		keypress.put(33,KeyEvent.VK_PAGE_UP);
		keypress.put(34,KeyEvent.VK_PAGE_DOWN);
		keypress.put(35,KeyEvent.VK_END);
		keypress.put(36,KeyEvent.VK_HOME);
		keypress.put(37,KeyEvent.VK_LEFT);
		keypress.put(38,KeyEvent.VK_UP);
		keypress.put(39,KeyEvent.VK_RIGHT);
		keypress.put(40,KeyEvent.VK_DOWN);
		keypress.put(45,KeyEvent.VK_INSERT);
		keypress.put(46,KeyEvent.VK_DELETE);
		keypress.put(48,KeyEvent.VK_0);
		keypress.put(49,KeyEvent.VK_1);
		keypress.put(50,KeyEvent.VK_2);
		keypress.put(51,KeyEvent.VK_3);
		keypress.put(52,KeyEvent.VK_4);
		keypress.put(53,KeyEvent.VK_5);
		keypress.put(54,KeyEvent.VK_6);
		keypress.put(55,KeyEvent.VK_7);
		keypress.put(56,KeyEvent.VK_8);
		keypress.put(57,KeyEvent.VK_9);
		keypress.put(65,KeyEvent.VK_A);
		keypress.put(66,KeyEvent.VK_B);
		keypress.put(67,KeyEvent.VK_C);
		keypress.put(68,KeyEvent.VK_D);
		keypress.put(69,KeyEvent.VK_E);
		keypress.put(70,KeyEvent.VK_F);
		keypress.put(71,KeyEvent.VK_G);
		keypress.put(72,KeyEvent.VK_H);
		keypress.put(73,KeyEvent.VK_I);
		keypress.put(74,KeyEvent.VK_J);
		keypress.put(75,KeyEvent.VK_K);
		keypress.put(76,KeyEvent.VK_L);
		keypress.put(77,KeyEvent.VK_M);
		keypress.put(78,KeyEvent.VK_N);
		keypress.put(79,KeyEvent.VK_O);
		keypress.put(80,KeyEvent.VK_P);
		keypress.put(81,KeyEvent.VK_Q);
		keypress.put(82,KeyEvent.VK_R);
		keypress.put(83,KeyEvent.VK_S);
		keypress.put(84,KeyEvent.VK_T);
		keypress.put(85,KeyEvent.VK_U);
		keypress.put(86,KeyEvent.VK_V);
		keypress.put(87,KeyEvent.VK_W);
		keypress.put(88,KeyEvent.VK_X);
		keypress.put(89,KeyEvent.VK_Y);
		keypress.put(90,KeyEvent.VK_Z);
		keypress.put(91,KeyEvent.VK_OPEN_BRACKET);
		keypress.put(92,KeyEvent.VK_BACK_SLASH);
		keypress.put(93,KeyEvent.VK_CLOSE_BRACKET);
		
		keypress.put(96,KeyEvent.VK_NUMPAD0);
		keypress.put(97,KeyEvent.VK_NUMPAD1);
		keypress.put(98,KeyEvent.VK_NUMPAD2);
		keypress.put(99,KeyEvent.VK_NUMPAD3);
		keypress.put(100,KeyEvent.VK_NUMPAD4);
		keypress.put(101,KeyEvent.VK_NUMPAD5);
		keypress.put(102,KeyEvent.VK_NUMPAD6);
		keypress.put(103,KeyEvent.VK_NUMPAD7);
		keypress.put(104,KeyEvent.VK_NUMPAD8);
		keypress.put(105,KeyEvent.VK_NUMPAD9);

		keypress.put(106,KeyEvent.VK_ASTERISK);
		keypress.put(107,KeyEvent.VK_PLUS);
		keypress.put(109,KeyEvent.VK_SUBTRACT);
		keypress.put(110,KeyEvent.VK_DECIMAL);
		keypress.put(111,KeyEvent.VK_DIVIDE);
		keypress.put(112,KeyEvent.VK_F1);
		keypress.put(113,KeyEvent.VK_F2);
		keypress.put(114,KeyEvent.VK_F3);
		keypress.put(115,KeyEvent.VK_F4);
		keypress.put(116,KeyEvent.VK_F5);
		keypress.put(117,KeyEvent.VK_F6);
		keypress.put(118,KeyEvent.VK_F7);
		keypress.put(119,KeyEvent.VK_F8);
		keypress.put(120,KeyEvent.VK_F9);
		keypress.put(121,KeyEvent.VK_F10);
		keypress.put(122,KeyEvent.VK_F11);
		keypress.put(123,KeyEvent.VK_F12);
		keypress.put(144,KeyEvent.VK_NUM_LOCK);
		keypress.put(145,KeyEvent.VK_SCROLL_LOCK);
		keypress.put(186,KeyEvent.VK_SEMICOLON);
		keypress.put(187,KeyEvent.VK_EQUALS);
		keypress.put(188,KeyEvent.VK_COMMA);
		keypress.put(189,KeyEvent.VK_MINUS);
		keypress.put(190,KeyEvent.VK_PERIOD);
		keypress.put(191,KeyEvent.VK_SLASH);		
//grave accent 	192		
		keypress.put(219,KeyEvent.VK_OPEN_BRACKET);
		keypress.put(220,KeyEvent.VK_BACK_SLASH);
		keypress.put(221,KeyEvent.VK_CLOSE_BRACKET);
		keypress.put(222,KeyEvent.VK_QUOTE);
	}
	
	
	public static Integer keyJavaCode(int javascriptKeyCode){
		return keypress.get(javascriptKeyCode);
	}
	
	public static Integer keyDownUpJavaCode(int javascriptKeyCode){
		return keydownup.get(javascriptKeyCode);
	}
	
	public static Collection<Integer> keypressJavascriptKeyCodes(){
		return keypress.keySet();
	}
	
	public static Collection<Integer> keyDownUpJavaKeyCodes(){
		return keydownup.values();
	}

	
	
	
	private static Rectangle screenRect;
	private static Robot robot;
	
	public static final String IMAGE_CODEC = "jpeg";
	
	
	private Screen() {
		// TODO Auto-generated constructor stub
	}
	
	static{
		try{
			robot = new Robot();
			screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	public static void key(int keycode){
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
	}
	
	public static void keyUp(int keycode){
		robot.keyRelease(keycode);
	}
	
	public static void keyDown(int keycode){
		robot.keyPress(keycode);
	}
	
	public static void mouseMove(int x,int y){
		robot.mouseMove(x, y);
	}

	public static void rightClick(){
		robot.keyPress(KeyEvent.VK_CONTEXT_MENU);
		robot.keyRelease(KeyEvent.VK_CONTEXT_MENU);
	}
	
	public static void mouseDown(){
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}
	
	public static void mouseUp(){
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	
	public static byte[] getScreen() throws IOException{
		BufferedImage im = robot.createScreenCapture(screenRect);
        ByteArrayOutputStream output = new ByteArrayOutputStream(500000);
        ImageIO.write(im, IMAGE_CODEC, output);
        byte[] img = output.toByteArray();
        return img;
	}
	
	public static byte[][] getScreen(int rows,int cols) throws IOException{
		BufferedImage image = robot.createScreenCapture(screenRect);
		int chunks = rows * cols;  
		int chunkWidth = image.getWidth() / cols; // determines the chunk width and height  
		int chunkHeight = image.getHeight() / rows;  
		int count = 0;  
		BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks  
		for (int x = 0; x < rows; x++) {  
			for (int y = 0; y < cols; y++) {  
				//Initialize the image array with image chunks  
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());  

				// draws the image chunk  
				Graphics2D gr = imgs[count++].createGraphics();  
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);  
				gr.dispose();  
			}  
		}  
		
		byte[][] ret = new byte[chunks][];
		
		for (int i = 0; i < imgs.length; i++) {  
			ByteArrayOutputStream output = new ByteArrayOutputStream(500000);
		    ImageIO.write(imgs[i], IMAGE_CODEC, output);
		    ret[i] = output.toByteArray();  
		}
		return ret;
	}

	
	
}