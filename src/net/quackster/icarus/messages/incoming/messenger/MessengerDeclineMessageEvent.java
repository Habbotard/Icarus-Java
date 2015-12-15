package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class MessengerDeclineMessageEvent implements Message {

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
