package net.quackster.icarus.messages.outgoing.messenger;

import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class MessengerUpdateMessageComposer extends Response {

	public MessengerUpdateMessageComposer(MessengerUser friend, boolean forceOffline) {
		
		this.init(Outgoing.FriendUpdateMessageComposer);
        this.appendInt32(0);
        this.appendInt32(1);
        this.appendInt32(0);
        friend.serialise(this, forceOffline);
        this.appendBoolean(false);
	}

}
