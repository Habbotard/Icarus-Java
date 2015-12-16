package net.quackster.icarus.game.room.populator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public class OfficialRoomPopulator  extends IRoomPopulator {
	
	@Override
	public List<Room> generateListing(boolean limit, Session session) {
		
		List<Room> rooms =  Icarus.getGame().getRoomManager().getPublicRooms();
		
		if (rooms == null) {
			return new ArrayList<Room>();
		}
		
		Comparator<? super Room> comparator = (room1, room2) -> 
										room2.getData().getUsersNow() - 
										room1.getData().getUsersNow();
		
		rooms.sort(comparator);
		return rooms;
	}
}
