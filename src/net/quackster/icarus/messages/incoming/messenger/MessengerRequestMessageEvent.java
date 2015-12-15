package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class MessengerRequestMessageEvent implements Message {

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
				response.appendString(user.getDetails().getUsername());
				response.appendString(user.getDetails().getFigure());
				user.getSession().send(response);

			}


		}
	}

}
