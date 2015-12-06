package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomUpdateMessageComposer extends Response {

	public RoomUpdateMessageComposer(int id) {
		this.init(Outgoing.RoomUpdateMessageComposer);
		this.appendInt32(id);
	}

}
