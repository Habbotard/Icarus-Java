package net.quackster.icarus.messages.outgoing.navigator;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class CreateRoomMessageComposer extends Response {

	public CreateRoomMessageComposer(int id, String name) {
		this.init(Outgoing.CreateRoomMessageComposer);
		this.appendInt32(id);
		this.appendString(name);
	}

}
