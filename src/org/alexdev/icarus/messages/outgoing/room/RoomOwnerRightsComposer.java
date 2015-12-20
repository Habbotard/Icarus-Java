package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomOwnerRightsComposer extends Response {

	public RoomOwnerRightsComposer(int id, boolean isOwner) {
		this.init(Outgoing.RoomOwnerRightsComposer);
		this.appendInt32(id);
		this.appendBoolean(isOwner);
	}

}
