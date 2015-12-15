package net.quackster.icarus.messages.outgoing.messenger;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RemoveFriendMessageComposer extends Response {

	public RemoveFriendMessageComposer(int friendId) {

		this.init(Outgoing.RemoveFriendMessageComposer);
        this.appendInt32(0);
        this.appendInt32(1);
        this.appendInt32(-1);
        this.appendInt32(friendId);
	}

}
