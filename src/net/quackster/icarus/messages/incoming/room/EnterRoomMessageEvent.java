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
		
		roomUser.setRoom(room);
		roomUser.setLoadingRoom(true);
		roomUser.getStatuses().clear();
		
        session.send(new InitialRoomInfoMessageComposer(room));
		
		if (!room.getFloor().equals("0")) {
		session.send(new RoomSpacesMessageComposer("floor", room.getFloor()));
		}
		
		if (!room.getWall().equals("0")) {
		session.send(new RoomSpacesMessageComposer("wallpaper", room.getWall()));
		}
		
		session.send(new RoomSpacesMessageComposer("landscape", room.getLandscape()));
		

		Response response = new Response(Outgoing.RoomRatingMessageComposer);
		
		if (roomUser.getRoom().getOwnerId() == session.getDetails().getId()) {
			response.appendInt32(4);
			roomUser.getStatuses().put("flatctrl 1", "");
		} else {
			response.appendInt32(0);
		}
        response.appendBoolean(false); // did i rate it and NOT owner
        session.send(response);
		
		response = new Response(Outgoing.RoomRightsLevelMessageComposer);
        response.appendInt32(4);
        session.send(response);
	
		session.send(new PrepareRoomMessageComposer(room));
	}

}