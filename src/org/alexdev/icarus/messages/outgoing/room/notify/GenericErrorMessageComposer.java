package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class GenericErrorMessageComposer extends Response {

	public GenericErrorMessageComposer(int errorCode) {
		this.init(Outgoing.GenericErrorMessageComposer);
		this.appendInt32(errorCode);
	}

}
