package thread;

import java.io.IOException;

public class CMD extends Thread {
	private String commandLine;
	private int exitValue;
	
	public CMD(String commandLine) {
		super();
		this.commandLine = commandLine;
	}
	
	
	@Override
	public void run() {
		/*Executer ex = new Executer();
		try {
			ex.execute(commandLine);
			exitValue = ex.getExitValue();
		} catch (Exception e1) {
			e1.printStackTrace();
		}*/
		
		Runtime runtime = Runtime.getRuntime();
	     Process proc = null;
	        
	     try {
	    	 proc = runtime.exec(commandLine);
	    	 exitValue = proc.waitFor();
	     } catch (IOException e) {
	    	 e.printStackTrace();
	     } catch (InterruptedException e) {
	    	 e.printStackTrace();
	     }
	}


	public int getExitValue() {
		return exitValue;
	}
}
