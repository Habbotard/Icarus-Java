package org.alexdev.icarus.game;

import org.alexdev.icarus.dao.Dao;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;

public class Game {

	private NavigatorManager navigator;
	private RoomManager room;
	private CatalogueManager catalogue;
	private FurnitureManager furniture;
	
	public Game(Dao dao) throws Exception {
		this.navigator = new NavigatorManager();
		this.room = new RoomManager();
		this.catalogue = new CatalogueManager();
		this.furniture = new FurnitureManager();
	}
	
	public void load() {
		
		try {
			this.room.load();
			this.navigator.load();
			this.catalogue.load();
			this.furniture.load();
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

	public CatalogueManager getCatalogue() {
		return catalogue;
	}

	public FurnitureManager getFurniture() {
		return furniture;
	}
}
