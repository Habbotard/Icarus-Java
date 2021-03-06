package org.alexdev.icarus.game.room.player;

import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.Point;

public class RoomSearch {

	private Room room;

	public RoomSearch(Room room) {
		this.room = room;
	}

	public IEntity findUser(Point coord) {

		try {
			return room.getEntities().stream().filter(session -> session.getRoomUser().getPoint().sameAs(coord) && !session.getRoomUser().isWalking()).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public void dispose() {
		this.room = null;
		
	}
}
