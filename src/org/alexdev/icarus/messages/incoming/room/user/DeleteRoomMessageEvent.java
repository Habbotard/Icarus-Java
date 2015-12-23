package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class DeleteRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (!room.hasRights(session, true)) {
			return;
		}
		
		request.readInt(); // room id
		
		for (Session users : room.getUsers()) {
			room.leaveRoom(users, true);
		}
		
		Icarus.getDao().getRoom().deleteRoom(room);

		room.dispose(true);
	}

}
