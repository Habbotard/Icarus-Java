package net.quackster.icarus.dao.mysql;

import net.quackster.icarus.dao.Dao;
import net.quackster.icarus.dao.INavigatorDao;
import net.quackster.icarus.dao.IPlayerDao;
import net.quackster.icarus.dao.IRoomDao;

public class MySQLDao implements Dao {

	public INavigatorDao navigator;
	public IRoomDao room;
	public IPlayerDao player;
	
	public MySQLDao() {
		this.navigator = new MySQLNavigatorDao();
		this.room = new MySQLRoomDao();
		this.player = new MySQLPlayerDao();
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

}
