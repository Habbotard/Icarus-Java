package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.messenger.RemoveFriendMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class MessengerDeleteFriendMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int amount = request.readInt();

		for (int i = 0; i < amount; i++) {

			int friendId = request.readInt();

			if (session.getMessenger().isFriend(friendId)) {

				MessengerUser friend = session.getMessenger().getFriend(friendId);
				
				if (friend.isOnline()) {
					friend.getSession().getMessenger().removeFriend(session.getDetails().getId());
					friend.getSession().send(new RemoveFriendMessageComposer(session.getDetails().getId()));
				}	
				
				session.getMessenger().removeFriend(friendId);
				session.send(new RemoveFriendMessageComposer(friendId));
				
				Icarus.getDao().getMessenger().removeFriend(friendId, session.getDetails().getId());
			}
		}
	}
}
