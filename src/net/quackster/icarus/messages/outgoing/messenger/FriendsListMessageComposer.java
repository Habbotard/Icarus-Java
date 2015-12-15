package net.quackster.icarus.messages.outgoing.messenger;

import java.util.List;

import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

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
