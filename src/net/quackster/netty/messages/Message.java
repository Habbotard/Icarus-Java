package net.quackster.netty.messages;

import net.quackster.icarus.game.user.Session;
import net.quackster.netty.readers.Request;

public interface Message {
	public void handle(Session session, Request request);
}
