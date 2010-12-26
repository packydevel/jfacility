package org.jfacility.java.lang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author luca
 */
public class SystemFileManager {
    
    private static String[] path = new String[] {"%%path%%"};
    private static Object[] explorer;
    
    public static boolean openExplorer(File path) throws IOException {
        if (path == null) 
            return false;
        JVM jvm = new JVM();
        if (jvm.isOrLater(16)) {
            java.awt.Desktop.getDesktop().open(path);
            return true;
        }
        while (path != null && !path.isDirectory())
            path = path.getParentFile();
        
        if (explorer == null)
            explorer = autoGetExplorerCommand();
        
        if (path != null && explorer != null) {
            final String spath = path.getAbsolutePath();
            final String[] paramsArray = (String[]) explorer[2];
            final int length = paramsArray.length;
            final String[] finalParams = new String[length];

            for (int i = 0; i < length; i++)
                finalParams[i] = paramsArray[i].replace("%%path%%", spath);

            runCommand((String) explorer[1], finalParams);
            return true;
        }

        return false;
    }
    
    private static Object[] autoGetExplorerCommand() {
        if (OS.isWindows())
            return new Object[] { "Explorer", "explorer", path};
        else if (OS.isMac())
            return new Object[] { "Open", "/usr/bin/open", path };
        else {
            final Object[][] programms = new Object[][] { 
                { "dolphin", path }, 
                { "konqueror", path }, 
                { "thunar", path }, 
                { "rox", path }, 
                { "pcmanfm", path }, 
                { "nautilus", new String[] { "--browser", "--no-desktop", "%%path%%" } } 
            };
            
            final String[] charset = System.getenv("PATH").split(":");
            for (String element : charset) {
                for (Object[] element2 : programms) {
                    final File fi = new File(element, (String) element2[0]);
                    if (fi.isFile()) 
                        return new Object[] { (String) element2[0], 
                                            fi.getAbsolutePath(), 
                                            element2[1] };
                }
            }
        }
        return null;
    }
    
    private static void runCommand(final String command, final String[] parameter) {
        final Executer exec = new Executer(command);
        exec.addParameters(parameter);
        exec.start();
    }
    
    public static void main (String[] args){
        try {
            String dir = "/home/luca/workspace";
            openExplorer(new File(dir));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

class Executer extends Thread implements Runnable{
    private String command;
    private ArrayList<String> parameter;
    private Process process;
    private int exitValue = -1;
    private boolean gotInterrupted = false;
    private Exception exception = null;

    public Executer(final String command) {
        super("Executer: " + command);
        this.command = command;
        this.parameter = new ArrayList<String>();
    }
    
    public void addParameters(final String[] par) {
        if (par == null) { return; }
        for (final String p : par) {
            this.parameter.add(p);
        }
    }
    
    @Override
    public void run() {
        if (this.command == null || this.command.trim().length() == 0)
            return;

        final ArrayList<String> params = new ArrayList<String>();
        params.add(this.command);
        params.addAll(this.parameter);
        
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
        } catch (final IOException e1) {
            this.exception = e1;
            return;
        }
    }
}