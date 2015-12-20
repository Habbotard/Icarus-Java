package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomSpacesMessageComposer extends Response {

	public RoomSpacesMessageComposer(String space, String data) {
		this.init(Outgoing.RoomSpacesMessageComposer);
		this.appendString(space);
		this.appendString(data);
	}

}
