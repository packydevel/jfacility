package org.jfacility;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfacility.exception.AlreadyStartedApplicationException;
import org.jfacility.lang.MySystem;

public class Application {

    public static Application APPLICATION;
    private static Class<?> CLASS;
    public static String ROOT_DIRECTORY;
    public static String HOME_DIRECTORY;
    public static boolean IS_JAR;
    public String JAR_FILENAME;
    private String name;
    private String author;
    private String build;
    private Boolean singleInstance = false;

    private Application() {
        Throwable t = new Throwable();
        StackTraceElement[] trace = t.getStackTrace();
        try {
            Class<?> caller = Class.forName(trace[trace.length - 1].getClassName());
            System.out.println(caller.getCanonicalName());
            CLASS = caller;
        } catch (Exception e) {
        }

        ROOT_DIRECTORY = retrieveRootDirectory();
        IS_JAR = isJar();
        JAR_FILENAME = retrieveJarFileName();
    }

    public static Application getInstance() {
        if (APPLICATION == null) {
            return new Application();
        }
        return APPLICATION;
    }

    public String getHomeDirectory() {
        try {
            return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean isJar() {
        String name = CLASS.getName().replaceAll("\\.", "/") + ".class";
        URL classURL = Thread.currentThread().getContextClassLoader().getResource(name);
        /*
         * caller is null in case the ressource is not found or not enough
         * rights, in that case we assume its not jared
         */
        System.out.println("The classURL is:" + classURL);
        if (classURL == null) {
            return false;
        }

        return classURL.toString().matches("jar\\:.*\\.jar\\!.*");
    }

    private String retrieveJarFileName() {
        if (isJar()) {
            String classFileName = CLASS.getName().replaceAll("\\.", "/") + ".class";
            URL classFileNameURL = Thread.currentThread().getContextClassLoader().getResource(classFileName);
            String classFileNameDecodedURL;

            try {
                classFileNameDecodedURL = URLDecoder.decode(
                        classFileNameURL.toString(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                classFileNameDecodedURL = "";
            }

            Pattern p = Pattern.compile(System.getProperty("file.separator")
                    + "[^" + System.getProperty("file.separator") + "]*\\.jar");

            Matcher m = p.matcher(classFileNameDecodedURL);
            m.find();
            System.out.println(classFileNameDecodedURL.substring(m.start() + 1,
                    m.end()));
            return (classFileNameDecodedURL.substring(m.start() + 1, m.end()));

        }
        return "";
    }

    private String retrieveRootDirectory() {
        String loc;
        String rootDirectory;

        if (isJar()) {
            // this is the jar file

            try {
                loc = URLDecoder.decode(
                        CLASS.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
            } catch (Exception e) {
                loc = CLASS.getProtectionDomain().getCodeSource().getLocation().getFile();
                System.err.println("failed urldecoding Location: " + loc);
            }
            File appRoot = new File(loc);
            if (appRoot.isFile()) {
                appRoot = appRoot.getParentFile();
            }
            rootDirectory = appRoot.getAbsolutePath();
        } else {
            rootDirectory = System.getProperty("user.home")
                    + System.getProperty("file.separator") + HOME_DIRECTORY
                    + System.getProperty("file.separator");
        }
        System.out.println(rootDirectory);
        return rootDirectory;
    }

    public void start() throws AlreadyStartedApplicationException {
        try {
            if (singleInstance) {
                JUnique.acquireLock(name);
            }
        } catch (AlreadyLockedException e) {
            throw new AlreadyStartedApplicationException();
        }
    }

    public void restart(String command) {
        ArrayList<String> commands = new ArrayList<String>();
        commands.add(MySystem.getJavaHome() + File.separator + "bin" + File.separator + "java");
        commands.add("-jar");
        commands.add(ROOT_DIRECTORY + File.separator + JAR_FILENAME);
        ProcessBuilder pb = new ProcessBuilder(commands);
        //ProcessBuilder pb = new ProcessBuilder("/usr/bin/java", "-jar",
        //        ROOT_DIRECTORY + File.separator + JAR_FILENAME);
        pb.redirectErrorStream(true);
        try {
            JUnique.releaseLock(name);
            Process p = pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        org.jfacility.lang.MySystem.shutdown();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public void enableSingleInstance(boolean flag) {
        this.singleInstance = flag;
    }
}