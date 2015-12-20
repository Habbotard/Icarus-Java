package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class AcceptUserInsideRoomMessageComposer extends Response {

	public AcceptUserInsideRoomMessageComposer() {
		this.init(Outgoing.AcceptUserInsideRoomMessageComposer);
		this.appendInt32(1);
	}
}
