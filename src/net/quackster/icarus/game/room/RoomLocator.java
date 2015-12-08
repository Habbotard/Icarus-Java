package net.quackster.icarus.game.room;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.pathfinder.Point;

public class RoomLocator {

	public static Session findUser(Room room, Point coord) {
		
		try {
			return room.getUsers().stream().filter(session -> session.getRoomUser().getPoint().sameAs(coord) && !session.getRoomUser().isWalking()).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
}
