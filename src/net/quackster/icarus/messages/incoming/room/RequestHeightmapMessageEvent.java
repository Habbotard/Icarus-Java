package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class RequestHeightmapMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}

		RoomModel roomModel = room.getModel();
		RoomUser roomUser = session.getRoomUser();

		Response response = new Response(Outgoing.HeightMapMessageComposer);
		response.appendInt32(roomModel.getMapSizeX() * roomModel.getMapSizeY());

		for (int i = 0; i < roomModel.getMapSizeY(); i++) {
			for (int j = 0; j < roomModel.getMapSizeX(); j++) {
				response.appendShort((int) (roomModel.getSquareHeight()[j][i] * 256));
			}
		}

		session.send(response);

		response = new Response(Outgoing.FloorMapMessageComposer);
		response.appendBoolean(true);
		response.appendInt32(room.getWallHeight());
		response.appendString(room.getModel().getFloorMap());
		session.send(response);
		
		
		session.getRoomUser().setLoadingRoom(false);
		session.getRoomUser().setInRoom(true);
		
		room.finaliseRoomEnter(session);
		//session.send(new RoomDataMessageComposer(room, session, false, false));
	}
}