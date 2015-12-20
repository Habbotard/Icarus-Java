package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class PrepareRoomMessageComposer extends Response {

	public PrepareRoomMessageComposer(Room room) {
		
		this.init(Outgoing.RoomUpdateMessageComposer);
		this.appendInt32(room.getData().getId());
	}
}
