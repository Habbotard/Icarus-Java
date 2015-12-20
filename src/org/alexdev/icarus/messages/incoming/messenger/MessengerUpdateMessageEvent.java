package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class MessengerUpdateMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		// update messeger ok
	}
}
