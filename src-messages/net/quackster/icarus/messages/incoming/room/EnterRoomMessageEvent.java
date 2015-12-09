package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.dao.room.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.engine.InitialRoomInfoMessageComposer;
import net.quackster.icarus.messages.outgoing.room.engine.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.engine.RoomSpacesMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class EnterRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = RoomDao.getRoom(request.readInt(), true);
		
		if (room == null) {
			return;
		}
		
		if (session.getRoomUser().inRoom()) {

			if (session.getRoomUser().getRoom() != room) {
				session.getRoomUser().getRoom().leaveRoom(session, false);
			}
		}
		
		session.getRoomUser().setRoom(room);
		session.getRoomUser().setLoadingRoom(true);
		session.getRoomUser().getStatuses().clear();
		
        session.send(new InitialRoomInfoMessageComposer(room));
		
		if (!room.getFloor().equals("0")) {
		session.send(new RoomSpacesMessageComposer("floor", room.getFloor()));
		}
		
		if (!room.getWall().equals("0")) {
		session.send(new RoomSpacesMessageComposer("wallpaper", room.getWall()));
		}
		
		session.send(new RoomSpacesMessageComposer("landscape", room.getLandscape()));
		

		Response response = new Response(Outgoing.RoomRatingMessageComposer);
        response.appendInt32(0);
        response.appendBoolean(false); // did i rate it and NOT owner
        session.send(response);
		
		response = new Response(Outgoing.RoomRightsLevelMessageComposer);
        response.appendInt32(0);
        session.send(response);
		
		session.send(new PrepareRoomMessageComposer(room));

	}

}
