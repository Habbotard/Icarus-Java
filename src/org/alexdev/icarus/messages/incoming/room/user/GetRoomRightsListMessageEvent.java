package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class GetRoomRightsListMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		System.out.println("XDDDDDDDDDDD");
	}

}
