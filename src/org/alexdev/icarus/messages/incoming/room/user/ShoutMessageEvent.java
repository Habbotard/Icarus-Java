package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class ShoutMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		RoomUser roomUser = session.getRoomUser();

		if (roomUser == null) {
			return;
		}

		Room room = roomUser.getRoom();

		if (room == null) {
			return;
		}
		
		roomUser.chat(request.readString(), request.readInt(),  request.readInt(), true, true);
	}

}
