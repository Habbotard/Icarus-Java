package net.quackster.icarus.messages.outgoing.room.notify;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class AcceptUserInsideRoomMessageComposer extends Response {

	public AcceptUserInsideRoomMessageComposer() {
		this.init(Outgoing.AcceptUserInsideRoomMessageComposer);
		this.appendInt32(1);
	}
}
