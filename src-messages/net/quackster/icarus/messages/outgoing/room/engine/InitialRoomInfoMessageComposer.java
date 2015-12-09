package net.quackster.icarus.messages.outgoing.room.engine;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class InitialRoomInfoMessageComposer extends Response {
	
	public InitialRoomInfoMessageComposer(Room room) {
		this.init(Outgoing.InitialRoomInfoMessageComposer);
		this.appendString(room.getModel().getName());
		this.appendInt32(room.getId());
	}
}
