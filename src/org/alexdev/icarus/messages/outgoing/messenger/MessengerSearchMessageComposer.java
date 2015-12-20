package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class MessengerSearchMessageComposer extends Response {

	public MessengerSearchMessageComposer(Session session, List<Integer> search) {

		List<MessengerUser> friends = new ArrayList<MessengerUser>();
		List<MessengerUser> strangers = new ArrayList<MessengerUser>();

		for (Integer id : search) {

			if (id != session.getDetails().getId()) {

				if (session.getMessenger().isFriend(id)) {
					friends.add(session.getMessenger().getFriend(id));
				} else {
					strangers.add(new MessengerUser(id));
				}
			}
		}

		this.init(Outgoing.MessengerSearchMessageComposer);
		
		this.appendInt32(friends.size());
		for (MessengerUser friend : friends) {
			friend.searchSerialise(this);
		}

		this.appendInt32(strangers.size());
		for (MessengerUser stranger : strangers) {
			stranger.searchSerialise(this);
			stranger.dispose();
		}
	}

}
