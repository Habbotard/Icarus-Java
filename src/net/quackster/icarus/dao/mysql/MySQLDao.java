package net.quackster.icarus.dao.mysql;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.Dao;
import net.quackster.icarus.dao.IMessengerDao;
import net.quackster.icarus.dao.INavigatorDao;
import net.quackster.icarus.dao.IPlayerDao;
import net.quackster.icarus.dao.IRoomDao;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLDao implements Dao {

	private INavigatorDao navigator;
	private IRoomDao room;
	private IPlayerDao player;
	private IMessengerDao messenger;

	private Storage storage;
	private boolean isConnected;

	public MySQLDao() {

		this.connect();

		this.navigator = new MySQLNavigatorDao(this);
		this.room = new MySQLRoomDao(this);
		this.player = new MySQLPlayerDao(this);
		this.messenger = new MySQLMessengerDao(this);

		//this.UpdateTable("users", new Object[] { "username",  "Alex" }, new Object[] { "id",  1 });
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

	/*public void UpdateTable (String table, Object[] set, Object[] where)
	{
		String query = "UPDATE " + table + " SET " + GenerateSQL(set) + " WHERE " + GenerateSQL(where);
		System.out.println(CreatePreparedArguments(ObjectArrays.concat(set, where, Object.class)));
	}

	public static String GenerateSQL(Object[] array) {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < array.length; i += 2) {
			
			Object value = array[i + 1];
			
			if (value instanceof Integer) {
				builder.append("'" + array[i] + "' = ? AND ");
			} else {
				builder.append("'" + array[i] + "' = ? AND ");
			}
		}

		return builder.toString().substring(0, builder.toString().length() - 5);
	}

	public static String CreatePreparedArguments(Object[] array) {
		StringBuilder builder = new StringBuilder();

		String preparedStatements = "";

		for (int i = 0; i < array.length; i += 2)
		{
			Object field = array[i];
			Object value = array[i + 1];

			builder.append("'" + array[i] + "' = ? AND ");

			if (value instanceof Integer) {
				preparedStatements += "set.setInt(" + i / 2 + ", " + value.toString() + ");\n";
			} else {
				preparedStatements += "set.setString(" + i / 2 + ", '" + value.toString() + "');\n";
			}
		}

		return preparedStatements; //builder.ToString().Substring(0, builder.ToString().Length - 5);
	}*/


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

}
