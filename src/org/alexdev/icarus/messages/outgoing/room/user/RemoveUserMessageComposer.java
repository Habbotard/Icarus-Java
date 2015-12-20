package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RemoveUserMessageComposer extends Response {

	public RemoveUserMessageComposer(int virtualId) {
		this.init(Outgoing.UserLeftRoomMessageComposer);
		this.appendString(virtualId + "");
	}

}
