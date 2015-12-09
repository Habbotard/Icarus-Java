package net.quackster.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class RoomUsersMessageComposer extends Response {

	public RoomUsersMessageComposer(Session session) {
		this(Arrays.asList(new Session[] { session }));
	}

	public RoomUsersMessageComposer(List<Session> users) {

		this.init(Outgoing.RoomUsersMessageComposer);

		synchronized (users) {

			this.appendInt32(users.size());

			for (Session session : users) {
				
				this.appendInt32(session.getDetails().getId());
				this.appendString(session.getDetails().getUsername());
				this.appendString(session.getDetails().getMotto());
				this.appendString(session.getDetails().getFigure());
				this.appendInt32(session.getDetails().getId()); // ROOM ENTITY ID: Haven't coded pets, nor bots, so it's just the user id
				this.appendInt32(session.getRoomUser().getX());
				this.appendInt32(session.getRoomUser().getY());
				this.appendString(Double.toString(session.getRoomUser().getHeight()));
				this.appendInt32(session.getRoomUser().getRotation());
				this.appendInt32(1);
				this.appendString("m");
				this.appendInt32(0);
				this.appendInt32(0);
				this.appendString("");
				this.appendString("");
				this.appendInt32(1337); // achievement points
				this.appendBoolean(false);
			}
		}
	}

}
