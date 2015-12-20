package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class FloorMapMessageComposer extends Response {

	public FloorMapMessageComposer(Room room) {
		this.init(Outgoing.FloorMapMessageComposer);
		this.appendBoolean(true);
		this.appendInt32(room.getData().getWallHeight());
		this.appendString(room.getData().getModel().getFloorMap());
	}

}
