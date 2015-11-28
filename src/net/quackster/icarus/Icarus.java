package net.quackster.icarus;

import java.util.Random;

import net.quackster.netty.connections.Connection;

public class Icarus {
	
	private static Connection server;
	private static Random random;

	public static void main(String[] args) {

		try {

			random = new Random();
			
			server = new Connection("127.0.0.1", 30000);
			server.configureNetty();
			server.listenSocket();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getServer() {
		return server;
	}

	public static Random getRandom() {
		return random;
	}
}
