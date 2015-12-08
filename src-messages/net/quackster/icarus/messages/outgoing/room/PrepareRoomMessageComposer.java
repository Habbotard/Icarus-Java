package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class PrepareRoomMessageComposer extends Response {

	public PrepareRoomMessageComposer() {
		this.init(Outgoing.PrepareRoomMessageComposer);
	}
}
