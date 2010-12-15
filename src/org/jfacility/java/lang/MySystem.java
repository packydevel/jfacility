package org.jfacility.java.lang;

import java.io.File;

/**
 *
 * @author luca
 */
public class MySystem {
    /** Restituisce la cartella di lavoro attuale dell'utente
     *
     * @return stringa percorso
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     *
     * @return
     */
    public static File getFileUserDir() {
        return new File(getUserDir());
    }

    /**
     * Restituisce la cartella home dell'utente
     *
     * @return stringa percorso
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     *
     * @return
     */
    public static File getFileUserHome() {
        return new File(getUserHome());
    }

    /**Restituisce il nome del sistema operativo
     *
     * @return stringa nome OS
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }
    /**Restituisce il nome del sistema operativo
     *
     * @return stringa nome OS
     */
    public static String getOsArchitecture() {
        return System.getProperty("os.arch");
    }
    /**Restituisce il nome del sistema operativo
     *
     * @return stringa nome OS
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    public static void printVariables() {
        for (String s : java.lang.System.getenv().keySet())
            System.out.println(s + "=" + java.lang.System.getenv(s));
    }

    public static void printProperties() {
        for (Object s : java.lang.System.getProperties().keySet())
            System.out.println(s + "=" + java.lang.System.getProperty((String) s));
    }

    /**Verifica se il sistema operativo in uso è windows
     *
     * @return true = windows, altrimenti false
     */
    public static boolean isWindows() {
        boolean windows = false;
        String osName = getOsName().toLowerCase();
        if (!osName.equals("linux")) {
            if ((osName.length() > 6)
                    && (osName.substring(0, 7).toLowerCase().equalsIgnoreCase("windows"))) {
                windows = true;
            }
        }
        return windows;
    }
}