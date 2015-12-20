package org.alexdev.icarus.dao;

public interface Dao {

	public boolean connect();
	
	public INavigatorDao getNavigator();
	public IRoomDao getRoom();
	public IPlayerDao getPlayer();
	public IMessengerDao getMessenger();
	
	public boolean isConnected();
}
