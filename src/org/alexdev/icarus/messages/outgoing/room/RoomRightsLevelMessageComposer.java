package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomRightsLevelMessageComposer extends Response {

	public RoomRightsLevelMessageComposer(int status) {
		this.init(Outgoing.RoomRightsLevelMessageComposer);
		this.appendInt32(status);
	}

}
