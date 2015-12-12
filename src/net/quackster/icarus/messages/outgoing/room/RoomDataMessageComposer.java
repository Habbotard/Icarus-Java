package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomDataMessageComposer extends Response {

	public RoomDataMessageComposer(Room room, Session session, boolean forwardPlayer) {
		
		this.init(Outgoing.RoomDataMessageComposer);
        this.appendBoolean(false); //flatId
        room.serialise(this, false, false);
        this.appendBoolean(forwardPlayer/* !session.InRoom*/); // isNotReload
        this.appendBoolean(false); // no staff pick
        this.appendBoolean(room.hasRights(session.getDetails().getId()));// bypass bell, pass ... - has fuse moderator
        this.appendBoolean(false); // is room muted?
        this.appendInt32(0); //WhoCanMute
        this.appendInt32(0); // WhoCanKick
        this.appendInt32(0); // WhoCanBan
        this.appendBoolean(false); // has rights
        this.appendInt32(0); // chat tye
        this.appendInt32(0); // chat balloon
        this.appendInt32(0); // chat speed
        this.appendInt32(14); // chat max distance
        this.appendInt32(0); // chat flood protection
	}

}
