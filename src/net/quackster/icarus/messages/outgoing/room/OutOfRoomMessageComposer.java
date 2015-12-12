package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class OutOfRoomMessageComposer extends Response {
	
	public OutOfRoomMessageComposer() {
		this.init(Outgoing.OutOfRoomMessageComposer);
	}
}
