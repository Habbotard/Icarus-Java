package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.messenger.RemoveFriendMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class MessengerDeleteFriendMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int amount = request.readInt();

		for (int i = 0; i < amount; i++) {

			int friendId = request.readInt();

			if (session.getMessenger().isFriend(friendId)) {
				
				System.out.println("Is friend: " + friendId);

				MessengerUser friend = session.getMessenger().getFriend(friendId);
				
				if (friend.isOnline()) {
					friend.getSession().send(new RemoveFriendMessageComposer(session.getDetails().getId()));
				}	
				
				session.send(new RemoveFriendMessageComposer(friendId));
			
				session.getMessenger().getRequests().remove(friend);
				Icarus.getDao().getMessenger().removeFriend(friendId, session.getDetails().getId());
			}
		}
	}
}
