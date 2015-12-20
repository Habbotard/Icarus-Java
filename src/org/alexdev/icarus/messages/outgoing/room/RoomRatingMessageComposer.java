package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomRatingMessageComposer extends Response {

	public RoomRatingMessageComposer(RoomUser roomUser, int score) {
		this.init(Outgoing.RoomRatingMessageComposer);
		this.appendInt32(score);
		this.appendBoolean(false);
	}

}
