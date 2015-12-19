package net.quackster.icarus.messages.outgoing.room.notify;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomSettingsUpdatedMessageComposer extends Response {

	public RoomSettingsUpdatedMessageComposer(Room room) {
		this.init(Outgoing.RoomSettingsUpdatedMessageComposer);
		this.appendInt32(room.getData().getId());
	}

}
