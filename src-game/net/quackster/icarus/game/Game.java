package net.quackster.icarus.game;

import net.quackster.icarus.dao.mysql.MySQLRoomDao;
import net.quackster.icarus.game.navigator.NavigatorManager;
import net.quackster.icarus.game.room.RoomManager;

public class Game {

	private NavigatorManager navigator;
	private RoomManager room;
	
	public Game() throws Exception {
		this.navigator = new NavigatorManager();
		this.room = new RoomManager();
	}
	
	public NavigatorManager getNavigatorManager() {
		return navigator;
	}

	public RoomManager getRoomManager() {
		return room;
	}
}
