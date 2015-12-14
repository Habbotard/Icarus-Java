package net.quackster.icarus.messages.outgoing.room.heightmap;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class FloorMapMessageComposer extends Response {

	public FloorMapMessageComposer(Room room) {
		this.init(Outgoing.FloorMapMessageComposer);
		this.appendBoolean(true);
		this.appendInt32(room.getData().getWallHeight());
		this.appendString(room.getData().getModel().getFloorMap());
	}

}
