package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;
import org.alexdev.icarus.util.GameSettings;

public class CanCreateRoomMessageComposer extends Response {

	public CanCreateRoomMessageComposer(Session session) {
		this.init(Outgoing.CanCreateRoomMessageComposer);
		this.appendInt32(session.getRooms().size() >= GameSettings.MAX_ROOMS_PER_ACCOUNT ? 1 : 0);
		this.appendInt32(GameSettings.MAX_ROOMS_PER_ACCOUNT);
	}

}
