package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.notify.AcceptUserInsideRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class DoorbellAnswerMessageEvent implements MessageEvent {

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
