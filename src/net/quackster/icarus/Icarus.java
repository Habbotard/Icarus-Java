package net.quackster.icarus;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Random;

import net.quackster.icarus.log.Log;
import net.quackster.icarus.netty.connections.Connection;
import net.quackster.icarus.util.Configuration;

public class Icarus {

	private static Connection server;
	private static Random random;
	private static Configuration configuration;

	private static final String REVISION = "PRODUCTION-201506161211-776084490";

	public static void main(String[] args) {

		try {

			File file = new File("icarus.properties");

			if (!file.isFile()) { 
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
				writeConfiguration(writer);
			}

			configuration = new Configuration(file);

			Log.startup();


			String IPAddress = "";

			try {
				IPAddress = configuration.get("server-ip");
			} catch (Exception e) {
				throw new Exception("There is no server-ip defined in icarus.properties");
			}

			int serverPort = 0;

			try {
				serverPort = Integer.valueOf(configuration.get("server-port"));
			} catch (Exception e) {
				throw new Exception("There is no server-port defined in icarus.properties");
			}

			Log.println("Settting up server");
			server = new Connection(IPAddress, serverPort);
			server.configureNetty();

			if (server.listenSocket()) {
				Log.println("Server is listening on " + IPAddress + ":" + serverPort);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		writer.flush();
		writer.close();

	}

	public static Connection getServer() {
		return server;
	}

	public static Random getRandom() {
		return random;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static String getRevision() {
		return REVISION;
	}
}
