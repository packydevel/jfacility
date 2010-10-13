package org.jfacility;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.jfacility.exception.AlreadyStartedApplicationException;

public class Application {

    private final Class<?> APPLICATION_CLASS = Application.class;
    private final String ROOT_DIRECTORY = getRootDirectory();
    private final String APPLICATION_HOME = getHome();
    private final String APPLICATION_JAR = getJarFile();
    private String name;
    private String author;
    private String build;
    private boolean singleInstance = false;

    public String getHome() {
        try {
            return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getJarFile() {
        try {
            return URLDecoder.decode(System.getProperty("java.class.path"),
                    Charset.defaultCharset().name());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isJarApplication(Class<?> c) {
        String name = c.getName().replaceAll("\\.", "/") + ".class";
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

    public String getRootDirectory() {
        return retrieveRootDirectory(APPLICATION_CLASS);
    }

    private String retrieveRootDirectory(Class<?> c) {
        String loc;
        String rootDirectory;

        if (isJarApplication(c)) {
            // this is the jar file

            try {
                loc = URLDecoder.decode(c.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
            } catch (Exception e) {
                loc = c.getProtectionDomain().getCodeSource().getLocation().getFile();
                System.err.println("failed urldecoding Location: " + loc);
            }
            File appRoot = new File(loc);
            if (appRoot.isFile()) {
                appRoot = appRoot.getParentFile();
            }
            rootDirectory = appRoot.getAbsolutePath();
        } else {
            rootDirectory = System.getProperty("user.home")
                    + System.getProperty("file.separator") + APPLICATION_HOME
                    + System.getProperty("file.separator");
        }
        System.out.println(rootDirectory);
        return rootDirectory;
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

    public static Application getInstance() {
        return new Application();
    }

    public void enableSingleInstance(boolean flag) {
        this.singleInstance = flag;
    }

    public void restart(String command) {
        ProcessBuilder pb = new ProcessBuilder("/usr/bin/java", "-jar",
                ROOT_DIRECTORY + "/" + name + ".jar");
        pb.redirectErrorStream(true);
        try {
            JUnique.releaseLock(name);
            Process p = pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        shutdown();
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

    public void shutdown() {
        System.exit(0);
    }
}
