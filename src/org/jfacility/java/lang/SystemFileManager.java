package org.jfacility.java.lang;

import java.io.File;
import java.io.IOException;

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
}