package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.settings.RoomType;

public class RoomManager {

	public List<Room> loadedRooms;

	public RoomManager() {
		this.loadedRooms = new ArrayList<Room>();
	}
	
	public void load() {
		Icarus.getDao().getRoom().getPublicRooms(true);
	}

	public void add(Room room) {

		boolean add = true;
		
		for (Room loadedRoom : this.loadedRooms) {

			if (room.getData().getId() == loadedRoom.getData().getId()) {
				add = false;
			}
		}

		if (add) {
			this.loadedRooms.add(room);
		}
	}
	
	public List<Room> getPublicRooms() {
		try {
			return this.loadedRooms.stream().filter(room -> room.getData().getRoomType() == RoomType.PUBLIC).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Room> getPlayerRooms(int userId) {
		try {
			return this.loadedRooms.stream().filter(room -> room.getData().getOwnerId() == userId).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}

	public Room find(int roomId) {

		try {
			return Icarus.getGame().getRoomManager().getLoadedRooms().stream().filter(r -> r.getData().getId() == roomId).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Room> getLoadedRooms() {
		return loadedRooms;
	}

}
