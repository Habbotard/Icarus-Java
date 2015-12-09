package net.quackster.icarus.messages.outgoing.room.engine;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class PrepareRoomMessageComposer extends Response {

	public PrepareRoomMessageComposer(Room room) {
		
		this.init(Outgoing.RoomUpdateMessageComposer);
		this.appendInt32(room.getId());
	}
}
