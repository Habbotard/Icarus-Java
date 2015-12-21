package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.outgoing.room.user.TypingStatusMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class TypingStatusMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		RoomUser roomUser = session.getRoomUser();

		if (roomUser == null) {
			return;
		}

		Room room = roomUser.getRoom();

		if (room == null) {
			return;
		}
		
		room.send(new TypingStatusMessageComposer(roomUser.getVirtualId(), request.getMessageId() == Incoming.StartTypingMessageEvent));

	}

}
