package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.FriendsListMessageComposer;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerRequestsMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class MessengerMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		if (session.getMessenger() == null) {
			return;
		}
		
		session.getMessenger().load();
		
		session.send(new MessengerCategoriesMessageComposer());
		session.send(new MessengerRequestsMessageComposer(session, session.getMessenger().getRequests()));
		session.send(new FriendsListMessageComposer(session.getMessenger().getFriends()));
		
		session.getMessenger().setInitalised(true);
	}

}