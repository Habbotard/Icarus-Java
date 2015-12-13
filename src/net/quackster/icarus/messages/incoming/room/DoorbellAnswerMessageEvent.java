package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.notify.AcceptUserInsideRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class DoorbellAnswerMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}

		String name = request.readString();
		boolean accept = request.readBoolean();

		if (name == null) {
			return;
		}

		Session client = Icarus.getServer().getSessionManager().findByName(name);

		if (client == null || client.getDetails() == null) {
			return;
		}

		if (!accept) {
			
			client.send(new GenericNoAnswerDoorbellMessageComposer());
			return;
		
		} else {
			
			client.send(new AcceptUserInsideRoomMessageComposer());
		}
	}

}
