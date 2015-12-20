package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomModelMessageComposer extends Response {
	
	public RoomModelMessageComposer(Room room) {
		this.init(Outgoing.InitialRoomInfoMessageComposer);
		this.appendString(room.getData().getModel().getName());
		this.appendInt32(room.getData().getId());
	}
}
