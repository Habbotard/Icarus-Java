package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomDataMessageComposer extends Response {

	public RoomDataMessageComposer(Room room, Session session, boolean isLoading, boolean stalkingRoom) {

		this.init(Outgoing.RoomDataMessageComposer);
		this.appendBoolean(isLoading);
		room.getData().serialise(this, isLoading);
		this.appendBoolean(stalkingRoom);
		this.appendBoolean(false); 
		this.appendBoolean(false);
		this.appendBoolean(false);
		this.appendInt32(false);
		this.appendInt32(false);
		this.appendInt32(false);
		this.appendBoolean(room.hasRights(session.getDetails().getId(), true));
		this.appendInt32(0);
		this.appendInt32(0);
		this.appendInt32(0);
		this.appendInt32(0);
		this.appendInt32(0);
	}
}
