package org.alexdev.icarus.messages;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.netty.readers.Request;

public interface MessageEvent {
	public void handle(Session session, Request request);
}
