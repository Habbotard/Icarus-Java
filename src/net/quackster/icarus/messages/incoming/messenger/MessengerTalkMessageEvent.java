package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class MessengerTalkMessageEvent implements Message {

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
