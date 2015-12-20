package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class GenericDoorbellMessageComposer extends Response {

	public GenericDoorbellMessageComposer(String username) {
		this.init(Outgoing.GenericDoorbellMessageComposer);
		this.appendString(username);
	}

	public GenericDoorbellMessageComposer(int notifyCode) {
		this.init(Outgoing.GenericDoorbellMessageComposer);
		this.appendInt32(notifyCode);
	}
}
