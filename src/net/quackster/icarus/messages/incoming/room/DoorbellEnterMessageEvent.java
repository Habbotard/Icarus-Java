package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

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
