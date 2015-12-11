package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.user.DanceMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class DanceMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		RoomUser roomUser = session.getRoomUser();

		if (roomUser == null) {
			return;
		}

		Room room = roomUser.getRoom();

		if (room == null) {
			return;
		}
		
		int danceId = request.readInt();
		
		if (danceId < 0 || danceId > 4)
            danceId = 0;
		
        if (danceId > 0) {/* && roomUserByHabbo.CarryItemId > 0)
            roomUserByHabbo.CarryItem(0);*/
        
        	// TODO: Stop carrying item if dancing
        }

        roomUser.getRoom().send(new DanceMessageComposer(roomUser.getVirtualId(), danceId));
        roomUser.setDanceId(danceId);
	}

}
