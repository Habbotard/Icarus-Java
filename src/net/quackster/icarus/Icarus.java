package net.quackster.icarus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;
import net.quackster.icarus.netty.connections.Connection;
import net.quackster.icarus.util.Util;

public class Icarus {

	private static Connection server;
	private static Util utilities;
	public static int PublicCount = 0;
	private static Storage mysql;

	private static final String REVISION = "PRODUCTION-201506161211-776084490";

	public static void main(String[] args) {

		try {

			
			
			createConfig();
			Log.startup();
			connectMySQL();
			startServer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createConfig() throws IOException {
		File file = new File("icarus.properties");

		if (!file.isFile()) { 
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
			writeConfiguration(writer);
			writer.flush();
			writer.close();
		}

		utilities = new Util();
	}
	
	private static void writeConfiguration(PrintWriter writer) {

		writer.println();
		writer.println("server-ip=127.0.0.1");
		writer.println("server-port=30000");
		writer.println();
		writer.println("mysql-hostname=127.0.0.1");
		writer.println("mysql-username=root");
		writer.println("mysql-password=changeme");
		writer.println("mysql-database=icarusdb");
		writer.println();
		writer.println("log-errors=true");
		writer.println("log-output=true");
		writer.println("log-connections=true");
		writer.println("log-packets=true");

	}
	
	private static void connectMySQL() {
		
		Log.println("Connecting to MySQL server");
		
		mysql = new Storage(utilities.getConfiguration().get("mysql-hostname"), utilities.getConfiguration().get("mysql-username"), utilities.getConfiguration().get("mysql-password"), utilities.getConfiguration().get("mysql-database")); {
			if (mysql.connectionFailed()) {
				Log.println("Could not connect");
			} else {
				Log.println("Connection to MySQL was a success");
			}
		}
		
		Log.println();
	}

	private static void startServer() {
		
		String IPAddress = utilities.getConfiguration().get("server-ip");
		int serverPort = Integer.valueOf(utilities.getConfiguration().get("server-port"));
		
		Log.println("Settting up server");
		
		server = new Connection(IPAddress, serverPort);
		server.configureNetty();

		if (server.listenSocket()) {
			Log.println("Server is listening on " + IPAddress + ":" + serverPort);
		} else {
			Log.println("Server could not listen on " + IPAddress + ":" + serverPort + ", please double check everything is correct in icarus.properties");
		}
	}

	public static Connection getServer() {
		return server;
	}

	public static String getRevision() {
		return REVISION;
	}

	public static Util getUtilities() {
		return utilities;
	}
}
