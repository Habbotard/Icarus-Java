package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class HotelViewMessageComposer extends Response {

	public HotelViewMessageComposer() {
		this.init(Outgoing.HotelScreenMessageComposer);
		this.appendInt32(3);
	}
}
