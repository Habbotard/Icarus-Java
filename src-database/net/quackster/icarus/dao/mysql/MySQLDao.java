package net.quackster.icarus.dao.mysql;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.Dao;
import net.quackster.icarus.dao.INavigatorDao;
import net.quackster.icarus.dao.IPlayerDao;
import net.quackster.icarus.dao.IRoomDao;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLDao implements Dao {

	private INavigatorDao navigator;
	private IRoomDao room;
	private IPlayerDao player;

	private Storage storage;
	private boolean isConnected;

	public MySQLDao() {
		
		this.connect();
		
		this.navigator = new MySQLNavigatorDao(this);
		this.room = new MySQLRoomDao(this);
		this.player = new MySQLPlayerDao(this);
	}

	@Override
	public boolean connect() {

		Log.println("Connecting to MySQL server");

		isConnected = true;
		
		storage = new Storage(Icarus.getUtilities().getConfiguration().get("mysql-hostname"), 
				Icarus.getUtilities().getConfiguration().get("mysql-username"), 
				Icarus.getUtilities().getConfiguration().get("mysql-password"), 
				Icarus.getUtilities().getConfiguration().get("mysql-database")); {

					isConnected = storage.isConnected();

					if (!isConnected) {
						Log.println("Could not connect");
					} else {
						Log.println("Connection to MySQL was a success");
					}
				}

		Log.println();

		return isConnected;
	}

	@Override
	public INavigatorDao getNavigator() {
		return this.navigator;
	}

	@Override
	public IRoomDao getRoom() {
		return this.room;
	}

	@Override
	public IPlayerDao getPlayer() {
		return this.player;
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	public Storage getStorage() {
		return storage;
	}

}
