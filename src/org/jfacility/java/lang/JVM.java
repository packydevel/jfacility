package org.jfacility.java.lang;

import java.io.File;

/**Classe che individua la java virtual machine in uso
 * 
 * @author luca
 */
public class JVM {

    public final static int JDK1_0 = 10;
    public final static int JDK1_1 = 11;
    public final static int JDK1_2 = 12;
    public final static int JDK1_3 = 13;
    public final static int JDK1_4 = 14;
    public final static int JDK1_5 = 15;
    public final static int JDK1_6 = 16;
    public final static int JDK1_7 = 17;
    public final static int JDK1_8 = 18;
    public final static int JDK1_9 = 19;
    
    private static JVM current;
    private int jdkVersion;

    static {
        current = new JVM();
    }

    /**@return the current JVM object
     */
    public static JVM current() {
        return current;
    }    

    /**
     * Creates a new JVM data from the <code>java.version</code> System property
     *
     */
    public JVM() {
        this(SystemProperty.getVersion());
    }

    /**
     * Constructor for the OS object
     */
    public JVM(String p_JavaVersion) {
        if (p_JavaVersion.startsWith("1.9."))
            jdkVersion = JDK1_9;
        else if (p_JavaVersion.startsWith("1.8."))
            jdkVersion = JDK1_8;
        else if (p_JavaVersion.startsWith("1.7."))
            jdkVersion = JDK1_7;
        else if (p_JavaVersion.startsWith("1.6."))
            jdkVersion = JDK1_6;
        else if (p_JavaVersion.startsWith("1.5."))
            jdkVersion = JDK1_5;
        else if (p_JavaVersion.startsWith("1.4."))
            jdkVersion = JDK1_4;
        else if (p_JavaVersion.startsWith("1.3."))
            jdkVersion = JDK1_3;
        else if (p_JavaVersion.startsWith("1.2."))
            jdkVersion = JDK1_2;
        else if (p_JavaVersion.startsWith("1.1."))
            jdkVersion = JDK1_1;
        else if (p_JavaVersion.startsWith("1.0."))
            jdkVersion = JDK1_0;
    }

    public boolean isOrLater(int p_Version) {
        return jdkVersion >= p_Version;
    }

    public boolean isOneDotOne() {
        return jdkVersion == JDK1_1;
    }

    public boolean isOneDotTwo() {
        return jdkVersion == JDK1_2;
    }

    public boolean isOneDotThree() {
        return jdkVersion == JDK1_3;
    }

    public boolean isOneDotFour() {
        return jdkVersion == JDK1_4;
    }

    public boolean isOneDotFive() {
        return jdkVersion == JDK1_5;
    }

    public boolean isOneDotSix() {
        return jdkVersion == JDK1_6;
    }

    public boolean isOneDotSeven() {
        return jdkVersion == JDK1_7;
    }

    public static String getCommand(){
        return SystemProperty.getHome() + File.separator + "bin" + File.separator + "java";
    }    
    
    public static boolean isRuntimeJavaSun(){
        return SystemProperty.getJavaRuntimeName().toLowerCase().startsWith("java");
    }
    
    public static boolean isRuntimeOpenJDK(){
        return SystemProperty.getJavaRuntimeName().toLowerCase().startsWith("openjdk");
    }
}