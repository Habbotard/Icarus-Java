package net.quackster.icarus.messages.outgoing.messenger;

import java.util.List;

import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

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
