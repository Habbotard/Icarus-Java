package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class TypingStatusMessageComposer extends Response {

	public TypingStatusMessageComposer(int virtualId, boolean typeStart) {
		this.init(Outgoing.TypingStatusMessageComposer);
		this.appendInt32(virtualId);
		this.appendInt32(typeStart);
	}

}
