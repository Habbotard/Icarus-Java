package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.SendPerkAllowancesMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserObjectMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class InfoRetrieveMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		session.send(new UserObjectMessageComposer(session.getDetails()));
		session.send(new SendPerkAllowancesMessageComposer());
	}

}
