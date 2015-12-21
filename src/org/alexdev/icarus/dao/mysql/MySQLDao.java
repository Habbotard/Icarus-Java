package org.alexdev.icarus.dao.mysql;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.Dao;
import org.alexdev.icarus.dao.ICatalogueDao;
import org.alexdev.icarus.dao.IFurnitureDao;
import org.alexdev.icarus.dao.IMessengerDao;
import org.alexdev.icarus.dao.INavigatorDao;
import org.alexdev.icarus.dao.IPlayerDao;
import org.alexdev.icarus.dao.IRoomDao;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.mysql.Storage;

public class MySQLDao implements Dao {

	private INavigatorDao navigator;
	private IRoomDao room;
	private IPlayerDao player;
	private IMessengerDao messenger;
	private ICatalogueDao catalogTab;
	private IFurnitureDao furniture;
	
	private Storage storage;
	private boolean isConnected;

	public MySQLDao() {

		this.connect();

		this.navigator = new MySQLNavigatorDao(this);
		this.room = new MySQLRoomDao(this);
		this.player = new MySQLPlayerDao(this);
		this.messenger = new MySQLMessengerDao(this);
		this.catalogTab = new MySQLCatalogueDao(this);
		this.furniture = new MySQLFurniture(this);

		//this.UpdateTable("users", new Object[] { "username",  "Alex" }, new Object[] { "id",  1 });
	}

	@Override
	public boolean connect() {

		Log.println("Connecting to MySQL server");

		storage = new Storage(Icarus.getUtilities().getConfiguration().get("mysql-hostname"), 
				Icarus.getUtilities().getConfiguration().get("mysql-username"), 
				Icarus.getUtilities().getConfiguration().get("mysql-password"), 
				Icarus.getUtilities().getConfiguration().get("mysql-database")); 


		isConnected = storage.isConnected();

		if (!isConnected) {
			Log.println("Could not connect");
		} else {
			Log.println("Connection to MySQL was a success");
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

	@Override
	public IMessengerDao getMessenger() {
		return messenger;
	}

	@Override
	public ICatalogueDao getCatalogue() {
		return catalogTab;
	}

	@Override
	public IFurnitureDao getFurniture() {
		return furniture;
	}

}
