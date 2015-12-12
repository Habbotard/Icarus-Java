package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomEnterErrorMessageComposer extends Response {
	
	public RoomEnterErrorMessageComposer(int errorCode) {
		this.init(Outgoing.RoomEnterErrorMessageComposer);
		this.appendInt32(errorCode);
	}
}
