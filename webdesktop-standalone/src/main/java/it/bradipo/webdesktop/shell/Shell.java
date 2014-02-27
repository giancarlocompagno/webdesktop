package it.bradipo.webdesktop.shell;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class Shell implements Runnable{
	
	InputStream in;
	OutputStream err;
	OutputStream out;
	
	
	

	public Shell(InputStream in, OutputStream err, OutputStream out) {
		super();
		this.in = in;
		this.err = err;
		this.out = out;
		new Thread(this).start();
	}
	
	public void run(){
		try{
			Scanner sc = new Scanner(System.in);
			ProcessBuilder pb = new ProcessBuilder("cmd.exe");
			Process p = pb.start();
			Thread t = new Thread(new Input(p.getInputStream(),this.out));
			Thread t1 = new Thread(new Input(p.getErrorStream(),this.err));
			Thread t2 = new Thread(new Output(p.getOutputStream(),this.in));

			t.start();
			t1.start();
			t2.start();

			t.join();
			t1.join();
			t2.join();
			sc.close();
		}catch(Exception e){

		}
	}
	
	
	static class Input implements Runnable{

		InputStream is;
		OutputStream os;
		
		public Input(InputStream is,OutputStream os) {
			this.is = is;
			this.os = os;
		}
		
		@Override
		public void run() {
			try {
				int i = -1;
				while((i = is.read())!=-1){
					os.write(i);
					os.flush();
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
				try{
					if(is!=null){
						os.close();
					}
				}catch(Exception e){
					
				}
			}
		}
	}
	
	static class Output implements Runnable{

		OutputStream os;
		InputStream is;
		
		public Output(OutputStream os,InputStream is) {
			this.os = os;
			this.is = is;
		}
		@Override
		public void run() {
			try {
				Scanner s = new Scanner(is);
				String command = null;
				while((command = s.nextLine())!=null){
					command = command+"\n";
					os.write(command.getBytes());
					os.flush();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try{
					if(is!=null){
						os.close();
					}
				}catch(Exception e){
					
				}
				try{
					if(is!=null){
						is.close();
					}
				}catch(Exception e){
					
				}
			}
			
		}
		
	}
	
	
}
