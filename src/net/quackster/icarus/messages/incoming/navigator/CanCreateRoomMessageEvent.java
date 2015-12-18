package net.quackster.icarus.messages.incoming.navigator;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.navigator.CanCreateRoomMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class CanCreateRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		session.send(new CanCreateRoomMessageComposer(session));
	}
}
