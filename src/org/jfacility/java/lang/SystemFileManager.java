package org.jfacility.java.lang;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author luca
 */
public class SystemFileManager {
    
	private static final String pathParameter = "%%path%%";
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
            final String[] optionList = (String[]) explorer[2];
            final String parameter = ((String) explorer[3]).replace("%%path%%", spath);

            runCommand((String) explorer[1], optionList, parameter);
            return true;
        }

        return false;
    }
    
    private static Object[] autoGetExplorerCommand() {
        if (OS.isWindows())
            return new String[] { "Explorer", "explorer", null, pathParameter};
        else if (OS.isMac())
            return new String[] { "Open", "/usr/bin/open", null, pathParameter };
        else {
            Object[][] programs = new Object[][] { 
                { "dolphin",null, null}, 
                { "konqueror",null, null},
                { "thunar",null, null},
                { "rox",null, null},
                { "pcmanfm",null, null},
                { "nautilus", new String[] { "--browser", "--no-desktop"}, null} 
            };
            
            for (Object[] program: programs) {
            	program[program.length-1] = pathParameter;
            }
            
            final String[] environmentPathElement = System.getenv("PATH").split(":");
            for (String element : environmentPathElement) {
                for (Object[] program : programs) {
                    final File fi = new File(element, (String) program[0]);
                    if (fi.isFile()) 
                        return new Object[] { (String) program[0], 
                                            fi.getAbsolutePath(), 
                                            program[1], program[2]};
                }
            }
        }
        return null;
    }
    
    private static void runCommand(final String command, final String[] optionList, final String parameter) {
        final Executer exec = new Executer(command);
        exec.setOptionList(optionList);
        exec.setParameter(parameter);
        exec.start();
    }
}