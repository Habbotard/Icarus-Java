package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class MessengerAcceptMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		int amount = request.readInt();
	
		for (int i = 0; i < amount; i++) {
			
			int toId = session.getDetails().getId();
			int fromId = request.readInt();
			
			Icarus.getDao().getMessenger().removeRequest(fromId, toId);
			Icarus.getDao().getMessenger().newFriend(toId, fromId);
			
			MessengerUser user = new MessengerUser(fromId);
			session.getMessenger().getFriends().add(user);
			
			if (user.isOnline()) {
				
				MessengerUser to = new MessengerUser(toId);
				
				user.getSession().getMessenger().getFriends().add(to);
				user.getSession().send(new MessengerUpdateMessageComposer(to, false));
			}
			
			session.send(new MessengerUpdateMessageComposer(user, false));
		}

	}

}
