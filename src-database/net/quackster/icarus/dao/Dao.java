package net.quackster.icarus.dao;

public interface Dao {

	public INavigatorDao getNavigator();
	public IRoomDao getRoom();
	public IPlayerDao getPlayer();
}
