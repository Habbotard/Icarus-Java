package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.messenger.Messenger;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.messenger.MessengerSearchMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class MessengerSearchMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String searchQuery = request.readString();
		
		if (searchQuery == null) {
			return;
		}

		Messenger messenger = session.getMessenger();
		
		if (messenger == null) {
			return;
		}
		
		if (!messenger.hasInitalised()) {
			return;
		}

		session.send(new MessengerSearchMessageComposer(session, Icarus.getDao().getMessenger().search(searchQuery)));
	}
}
