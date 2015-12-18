package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class SaveRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (!room.hasRights(session, true)) {
			return;
		}
		
		
	}

}
