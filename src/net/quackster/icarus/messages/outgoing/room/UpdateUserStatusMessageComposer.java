package net.quackster.icarus.messages.outgoing.room;

import java.util.List;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UpdateUserStatusMessageComposer extends Response {

	public UpdateUserStatusMessageComposer(List<Session> users) {
		
		this.init(Outgoing.UpdateUserStatusMessageComposer);
		this.appendInt32(users.size());
		
		for (Session user : users) {
			
			this.appendInt32(user.getDetails().getId());
			this.appendInt32(user.getRoomUser().getX());
			this.appendInt32(user.getRoomUser().getY());
			this.appendString(Double.toString(user.getRoomUser().getHeight()));
			this.appendInt32(user.getRoomUser().getBodyRotation());
			this.appendInt32(user.getRoomUser().getBodyRotation());

			String Status = "/";
			this.appendString(Status + "/");
		}
	}

}
