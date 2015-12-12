package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class DanceMessageComposer extends Response {

	public DanceMessageComposer(int virtualId, int danceId) {
		
		this.init(Outgoing.DanceStatusMessageComposer);
		this.appendInt32(virtualId);
		this.appendInt32(danceId);
	}

}
