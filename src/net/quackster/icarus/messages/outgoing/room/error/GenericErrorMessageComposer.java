package net.quackster.icarus.messages.outgoing.room.error;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class GenericErrorMessageComposer extends Response {

	public GenericErrorMessageComposer(int errorCode) {
		this.init(Outgoing.GenericErrorMessageComposer);
		this.appendInt32(errorCode);
	}

}
