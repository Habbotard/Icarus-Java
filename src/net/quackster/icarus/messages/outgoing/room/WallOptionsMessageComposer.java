package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class WallOptionsMessageComposer extends Response {

	public WallOptionsMessageComposer(Room room) {
		
		this.init(Outgoing.WallOptionsMessageComposer);
		this.appendBoolean(room.getData().isHideWall());
		this.appendInt32(room.getData().getWallThickness());
		this.appendInt32(room.getData().getFloorThickness());
		
	}
}
