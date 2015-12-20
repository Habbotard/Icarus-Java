package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RemoveFriendMessageComposer extends Response {

	public RemoveFriendMessageComposer(int friendId) {

		this.init(Outgoing.RemoveFriendMessageComposer);
        this.appendInt32(0);
        this.appendInt32(1);
        this.appendInt32(-1);
        this.appendInt32(friendId);
	}

}
