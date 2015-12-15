package net.quackster.icarus.messages.outgoing.messenger;

import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

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
