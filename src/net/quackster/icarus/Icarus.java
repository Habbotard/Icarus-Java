package net.quackster.icarus;

import net.quackster.icarus.network.Server;

public class Icarus {

	private static Server server;
	
	public static void run(String[] args) {

		try {
			server = new Server(32424);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static Server getServer() {
		return server;
	}
}
