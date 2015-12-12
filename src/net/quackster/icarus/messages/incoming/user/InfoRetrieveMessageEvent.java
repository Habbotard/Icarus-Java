package net.quackster.icarus.messages.incoming.user;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.user.SendPerkAllowancesMessageComposer;
import net.quackster.icarus.messages.outgoing.user.UserObjectMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class InfoRetrieveMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		session.send(new UserObjectMessageComposer(session.getDetails()));
		session.send(new SendPerkAllowancesMessageComposer());
	}

}
