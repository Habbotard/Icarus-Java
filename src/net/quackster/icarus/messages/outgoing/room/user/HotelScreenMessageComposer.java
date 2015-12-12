package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class HotelScreenMessageComposer extends Response {

	public HotelScreenMessageComposer() {
		this.init(Outgoing.HotelScreenMessageComposer);
		this.appendInt32(3);
	}
}
