package net.quackster.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public class PopularPopulator extends IRoomPopulator {

	@Override
	public List<Room> generateListing(boolean limit, Session session) {

		List<Room> rooms =  Icarus.getGame().getRoomManager().getLoadedRooms().stream().filter(r -> r.getUsersNow() > 0).collect(Collectors.toList());
		rooms.sort((room1, room2)->room2.getUsersNow()-room1.getUsersNow());

		return rooms;
	}

}
