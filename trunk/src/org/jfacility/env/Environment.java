package org.jfacility.env;

public class Environment {

	public static void printVariables() {
		for (String s : System.getenv().keySet()) {
			System.out.println(s + "=" + System.getenv(s));
		}
	}

	public static void printProperties() {
		for (Object s : System.getProperties().keySet()) {
			System.out.println(s + "=" + System.getProperty((String) s));
		}
	}
}