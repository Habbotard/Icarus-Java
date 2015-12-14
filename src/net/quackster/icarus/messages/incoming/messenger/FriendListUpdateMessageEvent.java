package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class FriendListUpdateMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		System.out.println("HELLO MES");
	}

}
