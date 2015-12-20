package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class FloodFilterMessageComposer extends Response {

	public FloodFilterMessageComposer(int waitSeconds) {
		this.init(Outgoing.FloodFilterMessageComposer);
		this.appendInt32(waitSeconds);
	}

}
