package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class DanceMessageComposer extends Response {

	public DanceMessageComposer(int virtualId, int danceId) {
		
		this.init(Outgoing.DanceStatusMessageComposer);
		this.appendInt32(virtualId);
		this.appendInt32(danceId);
	}

}
