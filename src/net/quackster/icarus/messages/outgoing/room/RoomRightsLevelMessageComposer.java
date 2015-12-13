package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomRightsLevelMessageComposer extends Response {

	public RoomRightsLevelMessageComposer(RoomUser roomUser) {
		this.init(Outgoing.RoomRightsLevelMessageComposer);
		this.appendInt32(0);
	}

}
