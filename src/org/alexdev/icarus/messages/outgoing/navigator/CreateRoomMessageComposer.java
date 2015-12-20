package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CreateRoomMessageComposer extends Response {

	public CreateRoomMessageComposer(int id, String name) {
		this.init(Outgoing.CreateRoomMessageComposer);
		this.appendInt32(id);
		this.appendString(name);
	}

}
