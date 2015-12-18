package net.quackster.icarus.messages.outgoing.navigator;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.util.GameSettings;

public class CanCreateRoomMessageComposer extends Response {

	public CanCreateRoomMessageComposer(Session session) {
		this.init(Outgoing.CanCreateRoomMessageComposer);
		this.appendInt32(session.getRooms().size() >= GameSettings.MAX_ROOMS_PER_ACCOUNT ? 1 : 0);
		this.appendInt32(GameSettings.MAX_ROOMS_PER_ACCOUNT);
	}

}
