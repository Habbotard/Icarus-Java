package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class TypingStatusMessageComposer extends Response {

	public TypingStatusMessageComposer(int virtualId, boolean typeStart) {
		this.init(Outgoing.TypingStatusMessageComposer);
		this.appendInt32(virtualId);
		this.appendInt32(typeStart);
	}

}
