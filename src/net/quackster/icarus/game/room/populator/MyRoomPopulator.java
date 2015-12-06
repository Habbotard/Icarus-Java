package net.quackster.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public class MyRoomPopulator extends IRoomPopulator {
	@Override
	public List<Room> generateListing(Session session) {
		
		System.out.println("IRoomGenerator");
		List<Room> rooms = Icarus.getGame().getRoomManager().getLoadedRooms().stream().filter(room -> room.getOwnerId() == 1).collect(Collectors.toList());
		return rooms;
	}

}
