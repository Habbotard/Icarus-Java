package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.messenger.FriendsListMessageComposer;
import net.quackster.icarus.messages.outgoing.messenger.MessengerCategoriesMessageComposer;
import net.quackster.icarus.messages.outgoing.messenger.MessengerRequestsMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class MessengerMessageEvent implements Message {

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