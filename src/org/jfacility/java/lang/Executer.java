package org.jfacility.java.lang;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author luca
 */
public class Executer extends Thread implements Runnable{
    private String command;
    private ArrayList<String> parameter;
    private Process process;
    private int exitValue = -1;
    private boolean gotInterrupted = false;
    private Exception exception = null;

    public Executer(final String cmd) {
        super();
        command = cmd;
        parameter = new ArrayList<String>();
    }
    
    public void addParameters(final String[] par) {
        if (par == null) { return; }
        for (final String p : par) {
            this.parameter.add(p);
        }
    }
    
    @Override
    public void run() {
        if (command == null || command.trim().length() == 0)
            return;

        final ArrayList<String> params = new ArrayList<String>();
        params.add(command);
        params.addAll(parameter);
        
        final ProcessBuilder pb = new ProcessBuilder(params.toArray(new String[] {}));

        try {
            this.process = pb.start();
            try {
                this.process.waitFor();
                this.exitValue = this.process.exitValue();
            } catch (final InterruptedException e1) {
                this.process.destroy();
                this.gotInterrupted = true;
            } catch (final Exception e) {
                e.printStackTrace();
            }            
            // must be called to clear interrupt flag
            Thread.interrupted();
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }
    }
}