package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class HotelViewMessageComposer extends Response {

	public HotelViewMessageComposer() {
		this.init(Outgoing.HotelScreenMessageComposer);
		this.appendInt32(3);
	}
}
