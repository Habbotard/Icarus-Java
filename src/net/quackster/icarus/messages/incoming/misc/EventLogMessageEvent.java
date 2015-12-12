package net.quackster.icarus.messages.incoming.misc;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class EventLogMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		session.getConnection().packetCheck();
	}

}