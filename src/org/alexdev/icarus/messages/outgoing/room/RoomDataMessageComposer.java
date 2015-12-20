package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class RoomDataMessageComposer extends Response {

	public RoomDataMessageComposer(Room room, Session session, boolean isLoading, boolean stalkingRoom) {

		this.init(Outgoing.RoomDataMessageComposer);
		this.appendBoolean(isLoading);
		
		room.getData().serialise(this, isLoading);
		//.appendResponse(new RoomInfoComposer(room, isLoading));
		this.appendBoolean(stalkingRoom); // stuff
		this.appendBoolean(false); // staff picked
		this.appendBoolean(false); // roomenterad (some hablet shit, I suppose to warn client when the user go to room!)
		this.appendBoolean(false); //room_moderation.mute_all.status
		this.appendInt32(false);
		this.appendInt32(false);
		this.appendInt32(false);
		this.appendBoolean(room.hasRights(session.getDetails().getId(), true)); //room_moderation.mute_all.enabled
		this.appendInt32(0);//Info.line_chatsettings);
		this.appendInt32(0);//Info.cloud_chatsettings);
		this.appendInt32(0);//Info.scroll_chatsettings);
		this.appendInt32(0);//Info.distance_chatsettings);
		this.appendInt32(0);//Info.flood_roomsettings); // flood settings
	}
}
