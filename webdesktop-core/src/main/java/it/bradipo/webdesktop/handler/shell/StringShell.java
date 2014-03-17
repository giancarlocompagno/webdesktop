package it.bradipo.webdesktop.handler.shell;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class StringShell implements Runnable{
	
	
	private boolean attiva = true;
	
	public boolean isAttiva() {
		return attiva;
	}
	
	private Process p;
	
	public void stop(){
		if(p!=null){
			p.destroy();
		}
		attiva=false;
	}
	
	private StringBuffer sb = new StringBuffer();
	
	private String command;
	
	private Object promptSync = new Object();
	private Object commandSync = new Object();
	
	protected abstract String getShellCommand();

	public StringShell() throws IOException {
		super();
		start();
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
			//final Process p = getProcess();
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
			attiva=false;
		}catch(Exception e){

		}
	}

	private void start() throws IOException {
		String shellCommand = getShellCommand();
		p = Runtime.getRuntime().exec(shellCommand);
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				p.destroy();
			}
		});
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
				attiva=false;
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
				attiva=false;
			}
			
		}
		
	}
	
	
}
