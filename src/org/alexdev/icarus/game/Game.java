package org.alexdev.icarus.game;

import org.alexdev.icarus.dao.Dao;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;

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
