package it.bradipo.webdesktop.handler.shell;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StringShell implements Runnable{
	
	private StringBuffer sb = new StringBuffer();
	
	private String command;
	
	private Object promptSync = new Object();
	private Object commandSync = new Object();

	public StringShell() {
		super();
		new Thread(this).start();
	}
	
	
	public void writeCommand(String command){
		synchronized (commandSync) {
			this.command=command;
			commandSync.notifyAll();
		}
	}
	
	public byte[] readCommand(){
		synchronized (commandSync) {
			if(command==null){
				try {
					commandSync.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return command();
			}else{
				return command();
			}
		}
		
	}


	private byte[] command() {
		String tmp = command;
		command = null;
		return (tmp+"\n").getBytes();
	}
	
	
	public String readPrompt(){
		synchronized (promptSync) {
			String s = sb.toString();
			sb.delete(0,  sb.length());
			sb.setLength(0);
			return s;
		}
	}
	
	public void writePrompt(String prompt){
		synchronized (promptSync) {
			sb.append(prompt);
		}
	}
	
	public void writePrompt(char prompt){
		synchronized (promptSync) {
			sb.append(prompt);
		}
	}
	
	
	public void run(){
		try{
			final Process p = Runtime.getRuntime().exec("cmd.exe");;
			
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					System.out.println("invocazione .....");
				}
			});
			Thread t = new Thread(new Input(p.getInputStream()));
			Thread t1 = new Thread(new Input(p.getErrorStream()));
			Thread t2 = new Thread(new Output(p.getOutputStream()));
			
			t.start();
			t1.start();
			t2.start();

			t.join();
			t1.join();
			t2.join();
			p.destroy();
		}catch(Exception e){

		}
	}
	
	
	class Input implements Runnable{
		InputStream is;
		public Input(InputStream is) {
			this.is = is;
		}
		
		@Override
		public void run() {
			try {
				int s = -1;
				while((s = is.read())!=-1){
					StringShell.this.writePrompt((char)s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					if(is!=null){
						is.close();
					}
				}catch(Exception e){
					
				}
			}
		}
	}
	
	class Output implements Runnable{

		OutputStream os;
		
		public Output(OutputStream os) {
			this.os = os;
		}
		@Override
		public void run() {
			try {
				byte[] command = null;
				while((command = StringShell.this.readCommand())!=null){
					os.write(command);
					os.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try{
					if(os!=null){
						os.close();
					}
				}catch(Exception e){
					
				}
			}
			
		}
		
	}
	
	
}
