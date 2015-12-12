package net.quackster.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserDisplayMessageComposer extends Response {

	public UserDisplayMessageComposer(Session session) {
		this(Arrays.asList(new Session[] { session }));
	}

	public UserDisplayMessageComposer(List<Session> users) {

		this.init(Outgoing.UserDisplayMessageComposer);
		
		synchronized (users) {

			this.appendInt32(users.size());

			for (Session session : users) {
				
				this.appendInt32(session.getDetails().getId()); // if id = 1 we can dance if we want to
				this.appendString(session.getDetails().getUsername());
				this.appendString(session.getDetails().getMotto());
				this.appendString(session.getDetails().getFigure());
				this.appendInt32(session.getRoomUser().getVirtualId());
				this.appendInt32(session.getRoomUser().getX());
				this.appendInt32(session.getRoomUser().getY());
				this.appendString(Double.toString(session.getRoomUser().getHeight()));
				this.appendInt32(0);
				this.appendInt32(1);
				this.appendString("m");
				this.appendInt32(-1);
				this.appendInt32(-1);
				this.appendInt32(0);
				this.appendInt32(1337); // achievement points
				this.appendBoolean(false);
			}
		}
	}

}
