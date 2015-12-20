package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class LeaveRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();
		
		if (room == null) {
			return;
		}

		room.leaveRoom(session, false);
	}

}
