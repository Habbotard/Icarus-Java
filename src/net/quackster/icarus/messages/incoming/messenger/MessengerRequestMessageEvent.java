package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class MessengerRequestMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String username = request.readString();
		
		if (username == null) {
			return;
		}
		
		

	}

}
