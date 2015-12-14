package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomModelMessageComposer extends Response {
	
	public RoomModelMessageComposer(Room room) {
		this.init(Outgoing.InitialRoomInfoMessageComposer);
		this.appendString(room.getData().getModel().getName());
		this.appendInt32(room.getData().getId());
	}
}
