package org.alexdev.icarus.messages.incoming.misc;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class LatencyTestMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		session.getConnection().packetCheck(request.getMessageId());
	}

}
