package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class MessengerAcceptMessageEvent implements Message {

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
