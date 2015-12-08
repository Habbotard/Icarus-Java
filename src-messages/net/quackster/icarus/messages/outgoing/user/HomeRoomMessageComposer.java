package net.quackster.icarus.messages.outgoing.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class HomeRoomMessageComposer extends Response {

	public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
		this.init(Outgoing.HomeRoomMessageComposer);
        this.appendInt32(2); 
        this.appendInt32(false); 
	}

}
