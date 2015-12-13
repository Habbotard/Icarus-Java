package net.quackster.icarus.messages.outgoing.room.notify;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class GenericNoAnswerDoorbellMessageComposer extends Response {

	public GenericNoAnswerDoorbellMessageComposer() {
		
		this.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
		this.appendString("");
	}
}
