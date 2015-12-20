package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class HomeRoomMessageComposer extends Response {

	public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
		this.init(Outgoing.HomeRoomMessageComposer);
        this.appendInt32(2); 
        this.appendInt32(false); 
	}

}
