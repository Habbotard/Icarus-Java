package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class FloodFilterMessageComposer extends Response {

	public FloodFilterMessageComposer(int waitSeconds) {
		this.init(Outgoing.FloodFilterMessageComposer);
		this.appendInt32(waitSeconds);
	}

}
