package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.netty.readers.Response;

public class RoomInfoComposer extends Response {
	
	public RoomInfoComposer(Room room, String enterRoom) {
		
		this.appendInt32(room.getData().getId());
		this.appendString(room.getData().getName());
		this.appendInt32(room.getData().getOwnerId());
		this.appendString(room.getData().getOwnerName());
		this.appendInt32(room.getData().getState().getStateCode());
		this.appendInt32(room.getData().getUsersNow());
		this.appendInt32(room.getData().getUsersMax());
		this.appendString(room.getData().getDescription());
		this.appendInt32(room.getData().getTradeState());
		this.appendInt32(room.getData().getScore());
		this.appendInt32(0); // Ranking
		this.appendInt32(room.getData().getCategory());
		this.appendInt32(0); //TagCount

		int enumType = true ? 32 : 0;
		
		// if has event
		//enumType += 4;

		if (room.getData().getRoomType().equals("private")) {
			enumType += 8;
		}

		if (room.getData().isAllowPets()) { 
			enumType += 16;
		}

		this.appendInt32(enumType);
		
		//this.appendString("Hello");
        //this.appendString("xd lolz");
        //this.appendInt32(100);
	}
}
