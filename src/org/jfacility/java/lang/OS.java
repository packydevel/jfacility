package org.jfacility.java.lang;

/**
 *
 * @author luca
 */
public class OS {
    
    
    private static final byte WINDOWS_NT = 0;
    private static final byte WINDOWS_2000 = 1;
    private static final byte WINDOWS_XP = 2;
    private static final byte WINDOWS_2003 = 3;
    private static final byte WINDOWS_VISTA = 4;
    private static final byte WINDOWS_7 = 5;
    private static final byte WINDOWS_OTHER = 6;
    private static final byte LINUX = 7;
    private static final byte MAC = 8;
    
    private static final String OS_NAME;
    private static byte OS_ID;
    
    static {
        OS_NAME = SystemProperty.getOsName();
        final String OS = OS_NAME.toLowerCase();
        if (OS.contains("windows 7"))
            OS_ID = WINDOWS_7;
        else if (OS.contains("windows xp"))
            OS_ID = WINDOWS_XP;
        else if (OS.contains("windows vista"))
            OS_ID = WINDOWS_VISTA;
        else if (OS.contains("windows 2000"))
            OS_ID = WINDOWS_2000;
        else if (OS.contains("windows 2003"))
            OS_ID = WINDOWS_2003;
        else if (OS.contains("nt"))
            OS_ID = WINDOWS_NT;
        else if (OS.contains("windows"))
            OS_ID = WINDOWS_OTHER;
        else if (OS.contains("mac"))
            OS_ID = MAC;
        else
            OS_ID = LINUX;        
    }
    
    /**Restituisce true se OS = linux     
     * 
     * @return
     */
    public static boolean isLinux() {
        return OS_ID == LINUX;
    }

    /**Restituisce true se OS = mac
     * 
     * @return
     */
    public static boolean isMac() {
        return OS_ID == MAC;
    }

    /**Restituisce true se OS = windows
     * 
     * @return
     */
    public static boolean isWindows() {
        switch (OS_ID) {
            case WINDOWS_XP:
            case WINDOWS_VISTA:
            case WINDOWS_2000:
            case WINDOWS_2003:
            case WINDOWS_NT:
            case WINDOWS_OTHER:
            case WINDOWS_7:
                return true;
        }
        return false;
    }
}