package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomSpacesMessageComposer extends Response {

	public RoomSpacesMessageComposer(String space, String data) {
		this.init(Outgoing.RoomSpacesMessageComposer);
		this.appendString(space);
		this.appendString(data);
	}

}
