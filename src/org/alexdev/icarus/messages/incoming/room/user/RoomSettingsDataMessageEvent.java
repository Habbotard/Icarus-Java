package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.room.RoomSettingsDataMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class RoomSettingsDataMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		int roomId = request.readInt();
		
		Room room = session.getRoomUser().getRoom();
		
		if (room.getData().getId() != roomId) {
			return;
		}
		
		session.send(new RoomSettingsDataMessageComposer(room));
	}

}
