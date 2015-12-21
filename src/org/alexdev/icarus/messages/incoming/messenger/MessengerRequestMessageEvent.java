package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

public class MessengerRequestMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		String username = request.readString();

		if (username == null) {
			return;
		}

		int userId = Icarus.getDao().getPlayer().getId(username);

		if (userId < 1) {
			return;
		}

		if (session.getMessenger().hasReqest(userId)) {
			return;
		}

		//TODO: Check if they have blocked friend requests

		if (Icarus.getDao().getMessenger().newRequest(session.getDetails().getId(), userId)) {

			MessengerUser user = new MessengerUser(userId);
			session.getMessenger().getRequests().add(user);

			if (user.isOnline()) {
				
				Response response = new Response(Outgoing.MessengerSendRequest);
				
				response.appendInt32(session.getDetails().getId());
				response.appendString(session.getDetails().getUsername());
				response.appendString(session.getDetails().getFigure());
				user.getSession().send(response);

			}


		}
	}

}
