package net.quackster.icarus.dao;

public interface Dao {

	public boolean connect();
	
	public INavigatorDao getNavigator();
	public IRoomDao getRoom();
	public IPlayerDao getPlayer();
	
	public boolean isConnected();
}
