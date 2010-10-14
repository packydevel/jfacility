package org.jfacility;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfacility.exception.AlreadyStartedApplicationException;

public class Application {
	private static Class<?> applicationClass;
	private static String ROOT_DIRECTORY;
	private final String APPLICATION_HOME = getHome();
	private final String APPLICATION_JAR = getJarFile();
	private String name;
	private String author;
	private String build;
	private Boolean singleInstance = false;

	public static void setApplicationClass(Class<?> applicationClass) {
		Application.applicationClass = applicationClass;
	}

	public String getHome() {
		try {
			return getClass().getProtectionDomain().getCodeSource()
					.getLocation().getPath();
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

	public String getRootDirectory() {
		return retrieveRootDirectory(applicationClass);
	}

	public boolean isJarApplication(Class<?> c) {
		String name = c.getName().replaceAll("\\.", "/") + ".class";
		URL classURL = Thread.currentThread().getContextClassLoader()
				.getResource(name);
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
	
	private String retrieveMainJarFile() {
		if (isJarApplication(applicationClass)) {
			String name = applicationClass.getName().replaceAll("\\.", "/") + ".class";
			URL classURL = Thread.currentThread().getContextClassLoader()
					.getResource(name);

			Pattern p = Pattern.compile(System.getProperty("file.separator") + "[^"
					+ System.getProperty("file.separator") + "]*\\.jar");
			try {
				Matcher m = p.matcher(URLDecoder.decode(classURL.toString(),
						"UTF-8"));
				m.find();
				System.out.println(URLDecoder.decode(classURL.toString(), "UTF-8")
						.substring(m.start() + 1, m.end()));
				return(URLDecoder.decode(classURL.toString(), "UTF-8")
						.substring(m.start() + 1, m.end()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	private String retrieveRootDirectory(Class<?> c) {
		String loc;
		String rootDirectory;

		if (isJarApplication(c)) {
			// this is the jar file

			try {
				loc = URLDecoder.decode(c.getProtectionDomain().getCodeSource()
						.getLocation().getFile(), "UTF-8");
			} catch (Exception e) {
				loc = c.getProtectionDomain().getCodeSource().getLocation()
						.getFile();
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

	public void restart(String command) {
		ProcessBuilder pb = new ProcessBuilder("/usr/bin/java", "-jar",
				getRootDirectory() + "/" + retrieveMainJarFile());
		pb.redirectErrorStream(true);
		try {
			JUnique.releaseLock(name);
			Process p = pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		shutdown();
	}

	public void enableSingleInstance(boolean flag) {
		this.singleInstance = flag;
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
