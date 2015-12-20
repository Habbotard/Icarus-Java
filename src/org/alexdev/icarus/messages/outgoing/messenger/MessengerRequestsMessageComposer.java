package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class MessengerRequestsMessageComposer extends Response {

	public MessengerRequestsMessageComposer(Session session, List<MessengerUser> requests) {
		this.init(Outgoing.MessengerRequestsMessageComposer);
		this.appendInt32(session.getDetails().getId());
		this.appendInt32(requests.size()); 

		for (MessengerUser user : requests) {
			this.appendInt32(user.getUserId());
			this.appendString(user.getDetails().getUsername());
			this.appendString(user.getDetails().getFigure());
		}
	}

}
