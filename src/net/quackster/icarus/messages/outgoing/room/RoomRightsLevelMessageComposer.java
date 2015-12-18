package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomRightsLevelMessageComposer extends Response {

	public RoomRightsLevelMessageComposer(int status) {
		this.init(Outgoing.RoomRightsLevelMessageComposer);
		this.appendInt32(status);
	}

}
