package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

public class MessengerTalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		int friendId = request.readInt();
		
		if (!session.getMessenger().isFriend(friendId)) {
			return;
		}
		
		MessengerUser friend = session.getMessenger().getFriend(friendId);
		
		if (friend.isOnline()) {
		
			//1933
			Response response = new Response(Outgoing.MessengerMessageComposer);
            response.appendInt32(session.getDetails().getId());
            response.appendString(request.readString());
            response.appendInt32(0);
            friend.getSession().send(response);
			
			
			
		} else {
			// TODO: Offline messaging
		}
	}

}
