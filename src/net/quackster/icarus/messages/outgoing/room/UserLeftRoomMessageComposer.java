package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserLeftRoomMessageComposer extends Response {

	public UserLeftRoomMessageComposer(int virtualId) {
		this.init(Outgoing.UserLeftRoomMessageComposer);
		this.appendString(virtualId + "");
	}

}
