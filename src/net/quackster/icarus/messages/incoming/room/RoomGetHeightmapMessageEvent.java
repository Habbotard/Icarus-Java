package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class RoomGetHeightmapMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}

		RoomModel roomModel = room.getModel();

		Response response = new Response(Outgoing.HeightMapMessageComposer);
		response.appendInt32(roomModel.getMapSizeX() * roomModel.getMapSizeY());

		for (int i = 0; i < roomModel.getMapSizeY(); i++) {
			for (int j = 0; j < roomModel.getMapSizeX(); j++) {
				response.appendShort((int) (roomModel.getSquareHeight()[j][i] * 26));
			}
		}

		session.send(response);

		response = new Response(Outgoing.FloorMapMessageComposer);
		response.appendBoolean(true);
		response.appendInt32(room.getWallHeight());

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < roomModel.getMapSizeY(); i++) {
			for (int j = 0; j < roomModel.getMapSizeX(); j++) {

				try {

					if (j == roomModel.getDoorX() && i == roomModel.getDoorY())	{
						stringBuilder.append(roomModel.getDoorZ());
					} else {

						stringBuilder.append(roomModel.getSquareChar()[j][i].toString());
					}
				}
				catch (Exception e) {
					stringBuilder.append("0");
				}
			}
			stringBuilder.append((char)13);
		}

		String s = stringBuilder.toString();
		response.appendString(s);
		session.send(response);
	}
}