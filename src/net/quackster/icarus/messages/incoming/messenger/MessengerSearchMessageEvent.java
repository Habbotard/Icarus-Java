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
			System.out.println("DEBUG 1");
			return;
		}

		Messenger messenger = session.getMessenger();
		
		if (messenger == null) {
			System.out.println("DEBUG 2");
			return;
		}
		
		if (!messenger.isInitalised()) {
			System.out.println("DEBUG 3");
			return;
		}

		session.send(new MessengerSearchMessageComposer(session, Icarus.getDao().getMessenger().search(searchQuery)));
	}
}
