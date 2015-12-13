package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomDataMessageComposer extends Response {

	public RoomDataMessageComposer(Room room, Session session, boolean isLoading, boolean stalkingRoom) {

		this.init(Outgoing.RoomDataMessageComposer);
		this.appendBoolean(isLoading);


		/*this.appendInt32(room.getId());
		this.appendString(room.getName());
		this.appendBoolean(false);//!Info.IsPublicRoom); // is pub?
		this.appendInt32(room.getOwnerId());
		this.appendString(room.getOwnerName());
		this.appendInt32(room.getState());
		this.appendInt32(room.getUsersNow());
		this.appendInt32(room.getUsersNow());
		this.appendString(room.getDescription());
		this.appendInt32(0); // Nothing else has ever been logged
		this.appendInt32(room.getTradeState()); //allowed Trading
		this.appendInt32(room.getScore());
		this.appendInt32(room.getScore() / 2); // ranking
		this.appendInt32(room.getCategory());
		this.appendInt32(0);
		this.appendInt32(0);*/
		
		room.serialise(this, isLoading);

		this.appendBoolean(stalkingRoom); // stuff
		this.appendBoolean(false); // staff picked
		this.appendBoolean(false); // roomenterad (some hablet shit, I suppose to warn client when the user go to room!)
		this.appendBoolean(false); //room_moderation.mute_all.status

		this.appendInt32(false);
		this.appendInt32(false);
		this.appendInt32(false);

		this.appendBoolean(false); //room_moderation.mute_all.enabled

		this.appendInt32(0);//Info.line_chatsettings);
		this.appendInt32(0);//Info.cloud_chatsettings);
		this.appendInt32(0);//Info.scroll_chatsettings);
		this.appendInt32(0);//Info.distance_chatsettings);
		this.appendInt32(0);//Info.flood_roomsettings); // flood settings
	}

	/*public RoomDataMessageComposer(Room room, Session session, boolean forwardPlayer, boolean doorbell) { 

		this.init(Outgoing.RoomDataMessageComposer);
        this.appendBoolean(doorbell); //flatId
        room.serialise(this, false, false);
        this.appendBoolean(forwardPlayer/* !session.InRoom); // isNotReload
        this.appendBoolean(false); // no staff pick
        this.appendBoolean(true);// bypass bell, pass ... - has fuse moderator
        this.appendBoolean(false); // is room muted?
        this.appendInt3232(0); //WhoCanMute
        this.appendInt3232(0); // WhoCanKick
        this.appendInt3232(0); // WhoCanBan
        this.appendBoolean(room.hasRights(session.getDetails().getId())); // has rights
        this.appendInt3232(0); // chat tye
        this.appendInt3232(0); // chat balloon
        this.appendInt3232(0); // chat speed
        this.appendInt3232(14); // chat max distance
        this.appendInt3232(0); // chat flood protection
	}

	public RoomDataMessageComposer(Room room, Session session, boolean isNotReload, boolean sendRoom, boolean show) {

		this.init(Outgoing.RoomDataMessageComposer);
        this.appendBoolean(show); //flatId
        room.serialise(this, false, false);
        this.appendBoolean(!isNotReload/* !session.InRoom); // isNotReload
        this.appendBoolean(isNotReload); // no staff pick
        this.appendBoolean(true);// bypass bell, pass ... - has fuse moderator
        this.appendBoolean(false); // is room muted?
        this.appendInt3232(0); //WhoCanMute
        this.appendInt3232(0); // WhoCanKick
        this.appendInt3232(0); // WhoCanBan
        this.appendBoolean(room.hasRights(session.getDetails().getId())); // has rights
        this.appendInt3232(0); // chat tye
        this.appendInt3232(0); // chat balloon
        this.appendInt3232(0); // chat speed
        this.appendInt3232(14); // chat max distance
        this.appendInt3232(0); // chat flood protection

	}*/

}
