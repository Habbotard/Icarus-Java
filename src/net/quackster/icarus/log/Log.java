package net.quackster.icarus.log;

import net.quackster.icarus.Icarus;

public class Log {

	private static String PREFIX = "ICARUS";

	public static void startup() {
		println("Icarus - Habbo Hotel Java Server");
		println("Loading server with revision: " + Icarus.getRevision());
		println();
	}
	
	private static String generateDataFormat() {
		return "[" + DateTime.now() + "]";//DateTime.now();
	}
	
	public static void println() {
		System.out.println(generateDataFormat() + " [" + PREFIX + "] ");
	}
	
	public static void println(Object format)  {
		System.out.println(generateDataFormat() + " [" + PREFIX + "] >> " + format.toString());
	}
	
	public static void printEmpty(Object format) {
		System.out.println("[" + PREFIX  + "] >> " + format.toString());
	}

	public static void exception(Exception e) {
		
		e.printStackTrace();
		
	}
	
}
