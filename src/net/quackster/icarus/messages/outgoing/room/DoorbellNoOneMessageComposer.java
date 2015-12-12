package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class DoorbellNoOneMessageComposer extends Response {

	public DoorbellNoOneMessageComposer() {
		this.init(Outgoing.DoorbellNoOneMessageComposer);
	}
}
