package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.netty.readers.Request;

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
