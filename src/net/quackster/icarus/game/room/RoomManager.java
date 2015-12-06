package net.quackster.icarus.game.room;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {

	public List<Room> loadedRooms;

	public RoomManager() {
		this.loadedRooms = new ArrayList<Room>();
	}

	public void add(Room room) {

		boolean add = true;
		
		for (Room loadedRoom : this.loadedRooms) {
			
			if (room.getId() != loadedRoom.getId()) {
				add = false;
			}
		}
		
		if (add) {
			this.loadedRooms.add(room);
		}
	}

	public List<Room> getLoadedRooms() {
		return loadedRooms;
	}
}
