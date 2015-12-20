package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class RoomInfoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		RoomUser roomUser = session.getRoomUser();

		boolean forwardPlayer = true;

		if (roomUser.inRoom()) {

			if (roomUser.getRoom() != room) {
				roomUser.getRoom().leaveRoom(session, false);
			} else {
				forwardPlayer = false;
			}
		}

		if (roomUser.isLoadingRoom()) {
			forwardPlayer = false;
		}

		if (forwardPlayer) {
			
			session.send(new RoomDataMessageComposer(room, session, request.readIntAsBool(), request.readIntAsBool()));
		}
	}

}
