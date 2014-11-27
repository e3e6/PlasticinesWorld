package org.simanyay.jizer.kernel;


import java.util.*;
import java.io.*;


public class Executer {
    @SuppressWarnings("unused")
	private List<String> err_output; // Error output
    @SuppressWarnings("unused")
	private List<String> nor_output; // Normal output
    private int exit_value;
    
    private final int ARRAY = 1;
    private final int STRING = 2;
    
    public void execute(String cmd) throws Exception {
        genericExec(cmd, STRING);
    }
    
    public void execute(String[] cmd) throws Exception {
        genericExec(cmd, ARRAY);
    }
    
    private void genericExec(Object cmd, int type) throws Exception {
        Runtime runtime = Runtime.getRuntime();
        Process proc = null;
        
        if(type == STRING) {
            proc = runtime.exec((String) cmd);
        } else if(type == ARRAY) {
            proc = runtime.exec((String[]) cmd);
        } else throw new IllegalArgumentException();
        
        Streamer err_stream = new Streamer(proc.getErrorStream());
        Streamer out_stream = new Streamer(proc.getInputStream());
        
        err_stream.run();
        out_stream.run();
        
        exit_value = proc.waitFor();
    }
    
    public int getExitValue() {
        return exit_value;
    }
}
// Я выкинул из этого класса явное указание типа 
// вывода (stdout или же stderr) поскольку в моей задаче это не требовалось.
// Добавить обратно труда не составит, тем более, что это есть в той статье
class Streamer extends Thread {
    private InputStream input;
    public Streamer(InputStream in) {
        input = in;
    }
    
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = null;
            
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}