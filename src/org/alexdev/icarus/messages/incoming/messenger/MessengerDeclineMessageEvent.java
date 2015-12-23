package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class MessengerDeclineMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		boolean deleteAll = request.readBoolean();

		if (!deleteAll) {

			/*int toId = */request.readInt();
			int fromId = request.readInt();
			
			Icarus.getDao().getMessenger().removeRequest(fromId, session.getDetails().getId());
			
		} else {
			
			for (MessengerUser user : session.getMessenger().getRequests()) {
				Icarus.getDao().getMessenger().removeRequest(user.getUserId(), session.getDetails().getId());
			}
			
			session.getMessenger().getRequests().clear();
		}
	}
}
