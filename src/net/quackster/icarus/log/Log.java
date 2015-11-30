package net.quackster.icarus.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.quackster.icarus.Icarus;

public class Log {

	private static String PREFIX = "ICARUS";

	public static void startup() {

		output("", false);
		output("-----------------------------------------", false);
		output("-- SERVER BOOT TIME: " + DateTime.now(), false);
		output("-----------------------------------------", false);
		output("", false);

		println("Icarus - Habbo Hotel Java Server");
		println("Loading server with revision: " + Icarus.getRevision());
		println();
	}

	private static String generateDataFormat() {
		return "[" + DateTime.now() + "]";//DateTime.now();
	}

	public static void println() {
		output(generateDataFormat() + " [" + PREFIX + "] ");
	}

	public static void println(Object format)  {
		output(generateDataFormat() + " [" + PREFIX + "] >> " + format.toString());
	}

	private static void output(String string) {
		output(string, true);
	}

	private static void output(String string, boolean log) {

		if (log) {
			System.out.println(string);
		}

		if (Icarus.getConfiguration().getBoolean("log-output")) {
			writeToFile("log/output.log", string);
		}

	}

	public static void exception(Exception e) {

		println("---------------------------------------------");
		println("Error has occured!");
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		println(exceptionAsString);
		println("---------------------------------------------");

		if (Icarus.getConfiguration().getBoolean("log-errors")) {
			writeToFile("log/error.log", "---------------------------------------------");
			writeToFile("log/error.log", " " + DateTime.now() + " - Error has occured!");
			writeToFile("log/error.log", exceptionAsString);
		}
	}
	
	private static void writeToFile(String dir, String string) {

		File file = new File(dir);
		
		try {

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		} catch (Exception e1) {

		}

		try {
			if (!file.exists()) { 	
				file.createNewFile();
			}

			PrintWriter writer =  new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
			writer.println(string);
			writer.flush();
			writer.close();


		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
