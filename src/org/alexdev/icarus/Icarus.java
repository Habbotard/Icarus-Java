package org.alexdev.icarus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.alexdev.icarus.dao.Dao;
import org.alexdev.icarus.dao.mysql.MySQLDao;
import org.alexdev.icarus.game.Game;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.netty.connections.Connection;
import org.alexdev.icarus.util.Util;

public class Icarus {

	private static Connection server;
	private static Util utilities;
	private static Game game;
	private static Dao dao;

	public static void main(String[] args) {

		try {
			createConfig();
			Log.startup();

			printSystemInfo();

			if (utilities.getConfiguration().get("database-type").equalsIgnoreCase("mysql")) {
				dao = new MySQLDao();
			}

			if (dao.isConnected()) {
				game = new Game(dao);
				game.load();
				
				startServer();
			}


		} catch (Exception e) {
			return;
		}
	}

	private static void printSystemInfo() {


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
		writer.println("database-type=mysql");
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

	public static Util getUtilities() {
		return utilities;
	}

	public static Game getGame() {
		return game;
	}

	public static Dao getDao() {
		return dao;
	}
}
