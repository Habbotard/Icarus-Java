package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class RoomSuccessMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		session.getRoomUser().setLoadingRoom(false);
		session.getRoomUser().setInRoom(true);
		//session.send(new RoomDataMessageComposer(session.getRoomUser().getRoom(), session, true));
	}

}
