package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.navigator.CanCreateRoomMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class CanCreateRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		session.send(new CanCreateRoomMessageComposer(session));
	}
}
