package org.jfacility.java.lang;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author luca
 */
public class Executer extends Thread {

    private String command;
    private ArrayList<String> optionList;
    private Process process;
    private int exitValue = -1;
    private boolean gotInterrupted = false;
    private Exception exception = null;

    public Executer(final String cmd) {
        super();
        command = cmd;
        optionList = new ArrayList<String>();
    }

    public void setOptionList(final String[] optionList) {
        if (optionList == null) {
            return;
        }
        for (final String p : optionList) {
            this.optionList.add(p);
        }
    }

    public void setParameter(final String parameter) {
        if (parameter == null) {
            return;
        }
        this.optionList.add(parameter);
    }

    @Override
    public void run() {
        if (command == null || command.trim().length() == 0) {
            return;
        }

        final ArrayList<String> params = new ArrayList<String>();
        params.add(command);
        params.addAll(optionList);

        final ProcessBuilder pb = new ProcessBuilder(
                params.toArray(new String[]{}));

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