package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

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
