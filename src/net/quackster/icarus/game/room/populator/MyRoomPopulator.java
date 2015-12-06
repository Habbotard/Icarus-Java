package net.quackster.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public class MyRoomPopulator extends IRoomPopulator {
	
	@Override
	public List<Room> generateListing(boolean limit, Session session) {
		
		// Find rooms by room ID ownership
		List<Room> rooms =  Icarus.getGame().getRoomManager().getLoadedRooms().stream().filter(room -> room.getOwnerId() == 1).collect(Collectors.toList());
		
		// Put rooms with higher count at top of list
		rooms.sort((room1, room2)->room2.getUsersNow()-room1.getUsersNow());
		
		return rooms;
	}

}
