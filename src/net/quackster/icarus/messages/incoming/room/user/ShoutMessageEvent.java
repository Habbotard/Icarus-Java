package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

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
