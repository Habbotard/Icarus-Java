package net.quackster.icarus.game.room.populator;

import java.util.List;

import net.quackster.icarus.dao.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public class MyRoomPopulator extends IRoomPopulator {
	
	@Override
	public List<Room> generateListing(boolean limit, Session session) {
		
		List<Room> rooms =  RoomDao.getPlayerRooms(session.getDetails());
		rooms.sort((room1, room2)->room2.getUsersNow()-room1.getUsersNow());
		
		return rooms;
	}

}
