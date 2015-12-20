package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class WallOptionsMessageComposer extends Response {

	public WallOptionsMessageComposer(Room room) {
		
		this.init(Outgoing.WallOptionsMessageComposer);
		this.appendBoolean(room.getData().isHideWall());
		this.appendInt32(room.getData().getWallThickness());
		this.appendInt32(room.getData().getFloorThickness());
		
	}
}
