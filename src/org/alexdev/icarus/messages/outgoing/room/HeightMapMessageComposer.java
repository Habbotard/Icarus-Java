package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class HeightMapMessageComposer extends Response {

	public HeightMapMessageComposer(RoomModel roomModel) {

		this.init(Outgoing.HeightMapMessageComposer);
		this.appendInt32(roomModel.getMapSizeX() * roomModel.getMapSizeY());
		for (int i = 0; i < roomModel.getMapSizeY(); i++) {
			for (int j = 0; j < roomModel.getMapSizeX(); j++) {
				this.appendShort((int) (roomModel.getSquareHeight()[j][i] * 256));
			}
		}
	}

}
