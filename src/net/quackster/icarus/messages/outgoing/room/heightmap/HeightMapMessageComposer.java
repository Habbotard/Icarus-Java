package net.quackster.icarus.messages.outgoing.room.heightmap;

import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

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
