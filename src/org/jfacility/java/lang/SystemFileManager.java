package org.jfacility.java.lang;

import java.io.File;

/**
 *
 * @author luca
 */
public class SystemFileManager {
    
    private static Object[] autoGetExplorerCommand() {
        if (OS.isWindows())
            return new Object[] { "Explorer", "explorer", new String[] { "%%path%%" } };
        else if (OS.isMac())
            return new Object[] { "Open", "/usr/bin/open", new String[] { "%%path%%" } };
        else {
            final Object[][] programms = new Object[][] { 
                { "dolphin", new String[] { "%%path%%" } }, 
                { "konqueror", new String[] { "%%path%%" } }, 
                { "thunar", new String[] { "%%path%%" } }, 
                { "rox", new String[] { "%%path%%" } }, 
                { "pcmanfm", new String[] { "%%path%%" } }, 
                { "nautilus", new String[] { "--browser", "--no-desktop", "%%path%%" } } 
            };
            
            final String[] charset = System.getenv("PATH").split(":");
            for (String element : charset) {
                for (Object[] element2 : programms) {
                    final File fi = new File(element, (String) element2[0]);
                    if (fi.isFile()) 
                        return new Object[] { (String) element2[0], fi.getAbsolutePath(), element2[1] };
                }
            }            
        }
        return null;
    }

}