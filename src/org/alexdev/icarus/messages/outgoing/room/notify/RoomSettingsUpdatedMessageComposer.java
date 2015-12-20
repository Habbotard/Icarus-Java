package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomSettingsUpdatedMessageComposer extends Response {

	public RoomSettingsUpdatedMessageComposer(Room room) {
		this.init(Outgoing.RoomSettingsUpdatedMessageComposer);
		this.appendInt32(room.getData().getId());
	}

}
