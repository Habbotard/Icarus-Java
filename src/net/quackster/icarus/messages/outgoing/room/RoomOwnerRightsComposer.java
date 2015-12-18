package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomOwnerRightsComposer extends Response {

	public RoomOwnerRightsComposer(int id, boolean isOwner) {
		this.init(Outgoing.RoomOwnerRightsComposer);
		this.appendInt32(id);
		this.appendBoolean(isOwner);
	}

}
