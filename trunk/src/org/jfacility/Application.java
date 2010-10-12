package org.jfacility;

import it.sauronsoftware.junique.JUnique;

public class Application {
	
	public static void restart(String command) {
		ProcessBuilder pb = new ProcessBuilder("/usr/bin/java","-jar /packydevel/eclipse/[Java]\\ -\\ FeedWorker\\ -\\ [Test]/FeedWorker.jar");
		try {
		JUnique.releaseLock("FeedWorkerClient");
		pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		shutdown();
	}
	
	public static void shutdown() {
		System.exit(0);
	}
}
