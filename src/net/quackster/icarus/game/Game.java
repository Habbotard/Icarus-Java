package net.quackster.icarus.game;

import net.quackster.icarus.dao.Dao;
import net.quackster.icarus.game.navigator.NavigatorManager;
import net.quackster.icarus.game.room.RoomManager;
import net.quackster.icarus.log.Log;

public class Game {

	private NavigatorManager navigator;
	private RoomManager room;
	
	public Game(Dao dao) throws Exception {
		this.navigator = new NavigatorManager();
		this.room = new RoomManager();
	}
	
	public void load() {
		
		try {
			this.room.load();
			this.navigator.load();
		} catch (Exception e) {
			Log.exception(e);
		}
	}
	
	public NavigatorManager getNavigatorManager() {
		return navigator;
	}

	public RoomManager getRoomManager() {
		return room;
	}
}
