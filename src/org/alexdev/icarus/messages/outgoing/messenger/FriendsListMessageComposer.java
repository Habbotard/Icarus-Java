package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class FriendsListMessageComposer extends Response {

	public FriendsListMessageComposer(List<MessengerUser> friends) {
		this.init(Outgoing.InitMessengerMessageComposer);
		this.appendInt32(1);
		this.appendInt32(0);
		this.appendInt32(friends.size());
		
		for (MessengerUser friend : friends) {
			friend.update();
			friend.serialise(this, false);
		}
	}
}
