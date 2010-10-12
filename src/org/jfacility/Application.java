package org.jfacility;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import org.jfacility.exception.AlreadyStartedApplicationException;

public class Application {

	private String name;
	private String author;
	private String build;
	private Boolean singleInstance = false;

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
				"/packydevel/eclipse/[Java] - FeedWorker - [Test]/FeedWorker.jar");
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
			JUnique.acquireLock(name);
		} catch (AlreadyLockedException e) {
			throw new AlreadyStartedApplicationException();
		}
	}

	public void shutdown() {
		System.exit(0);
	}
}
