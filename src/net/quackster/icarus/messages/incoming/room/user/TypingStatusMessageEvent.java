package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.outgoing.room.user.TypingStatusMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class TypingStatusMessageEvent implements Message {

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
