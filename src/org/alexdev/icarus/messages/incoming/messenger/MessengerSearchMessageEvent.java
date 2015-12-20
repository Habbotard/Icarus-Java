package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerSearchMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

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
