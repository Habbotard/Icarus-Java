package net.quackster.icarus.messages.outgoing.room.notify;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomSettingsOKMessageComposer extends Response {

	public RoomSettingsOKMessageComposer(Room room) {
		this.init(Outgoing.RoomSettingsOKMessageComposer);
		this.appendInt32(room.getData().getId());
	}

}
