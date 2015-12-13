package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomRatingMessageComposer extends Response {

	public RoomRatingMessageComposer(RoomUser roomUser, int score) {
		this.init(Outgoing.RoomRatingMessageComposer);
		this.appendInt32(score);
		this.appendBoolean(false);
	}

}
