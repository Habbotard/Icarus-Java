package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class GenericNoAnswerDoorbellMessageComposer extends Response {

	public GenericNoAnswerDoorbellMessageComposer() {
		
		this.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
		this.appendString("");
	}
}
