package net.quackster.icarus.messages.outgoing.room.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RemoveUserMessageComposer extends Response {

	public RemoveUserMessageComposer(int virtualId) {
		this.init(Outgoing.UserLeftRoomMessageComposer);
		this.appendString(virtualId + "");
	}

}
