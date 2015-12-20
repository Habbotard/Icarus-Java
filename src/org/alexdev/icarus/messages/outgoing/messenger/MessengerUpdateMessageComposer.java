package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

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
