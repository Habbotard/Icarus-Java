package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomEnterErrorMessageComposer extends Response {
	
	public RoomEnterErrorMessageComposer(int errorCode) {
		this.init(Outgoing.RoomEnterErrorMessageComposer);
		this.appendInt32(errorCode);
	}
}
