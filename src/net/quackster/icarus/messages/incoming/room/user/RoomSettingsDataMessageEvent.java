package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.RoomSettingsDataMessageComposer;
import net.quackster.icarus.netty.readers.Request;

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
