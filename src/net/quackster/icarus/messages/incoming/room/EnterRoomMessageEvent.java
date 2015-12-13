package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.InitialRoomInfoMessageComposer;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class EnterRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);
		
		if (room == null) {
			return;
		}
		
		RoomUser roomUser = session.getRoomUser();
		
		
	}

}
