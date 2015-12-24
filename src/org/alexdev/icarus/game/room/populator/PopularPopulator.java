package org.alexdev.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;

public class PopularPopulator extends IRoomPopulator {

	@Override
	public List<Room> generateListing(boolean limit, Session session) {

		List<Room> rooms =  Icarus.getGame().getRoomManager().getLoadedRooms().stream().filter(r -> r.getData().getUsersNow() > 0).collect(Collectors.toList());
		
		rooms.sort((room1, room2)
		->room2.getData().getUsersNow()
		- room1.getData().getUsersNow());

		return rooms;
	}

}
