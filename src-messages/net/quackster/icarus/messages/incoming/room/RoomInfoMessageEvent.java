package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.dao.room.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.engine.RoomDataMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class RoomInfoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = RoomDao.getRoom(request.readInt(), true);
		
		if (room == null) {
			return;
		}
		
		SessionRoom roomUser = session.getRoomUser();
		
		boolean forwardPlayer = true;
		
		if (roomUser.inRoom()) {

			if (roomUser.getRoom() != room) {
				roomUser.getRoom().leaveRoom(session, false);
			}
			
			if (roomUser.getRoom() == room) {
				forwardPlayer = false;
			}
		}
		
		if (roomUser.isLoadingRoom()) {
			return;
		} 
		
		session.send(new RoomDataMessageComposer(room, session, forwardPlayer));

	}

}
