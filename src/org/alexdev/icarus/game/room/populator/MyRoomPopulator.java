package org.alexdev.icarus.game.room.populator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;

public class MyRoomPopulator extends IRoomPopulator {
	
	@Override
	public List<Room> generateListing(boolean limit, Session session) {
		
		List<Room> rooms =  Icarus.getGame().getRoomManager().getPlayerRooms(session.getDetails().getId());
		
		if (rooms == null) {
			rooms = new ArrayList<Room>();
		}
		
		rooms.sort((room1, room2) -> 
		room2.getData().getUsersNow() - 
		room1.getData().getUsersNow());
		
		return rooms;
	}

}
