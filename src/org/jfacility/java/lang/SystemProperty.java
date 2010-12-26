package org.jfacility.java.lang;

import java.io.File;

/**
 *
 * @author luca
 */
public class SystemProperty {
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
    
    /**Restituisce la versione in uso di java
     *
     * @return Stringa versione java
     */
    public static String getVersion() {
        return System.getProperty("java.version");
    }
    /**Restituisce vendor in uso di java
     *
     * @return Stringa vendor java
     */
    public static String getVendor() {
        return System.getProperty("java.vendor");
    }

    public static String getHome() {
        return System.getProperty("java.home");
    }
    
    public static String getTempDir(){
        return System.getProperty("java.io.tmpdir");
    }

    public static void printVariables() {
        for (String s : java.lang.System.getenv().keySet())
            System.out.println(s + "=" + java.lang.System.getenv(s));
    }

    public static void printProperties() {
        for (Object s : java.lang.System.getProperties().keySet())
            System.out.println(s + "=" + java.lang.System.getProperty((String) s));
    }
}