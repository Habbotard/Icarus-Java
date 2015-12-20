package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class DoorbellEnterMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		int roomId = request.readInt();
		
		Room room = Icarus.getGame().getRoomManager().find(roomId);
		
		if (room == null) {
			return;
		}
		
		room.loadRoom(session);
	}
}
