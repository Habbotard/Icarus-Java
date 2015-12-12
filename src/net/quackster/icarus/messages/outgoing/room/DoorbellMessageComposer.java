package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class DoorbellMessageComposer extends Response {

	public DoorbellMessageComposer(String username) {
		this.init(Outgoing.DoorbellMessageComposer);
		this.appendString(username);
	}

}
