package net.quackster.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserStatusMessageComposer extends Response {

	public UserStatusMessageComposer(Session session) {
		this.compose(Arrays.asList(new Session[] { session }));
	}


	public UserStatusMessageComposer(List<Session> users) {
		this.compose(users);
	}


	public void compose(List<Session> users) {

		this.init(Outgoing.UserStatusMessageComposer);
		
		synchronized (users) {

			this.appendInt32(users.size());

			for (Session user : users) {

				this.appendInt32(user.getRoomUser().getVirtualId());
				this.appendInt32(user.getRoomUser().getX());
				this.appendInt32(user.getRoomUser().getY());
				this.appendString(Double.toString(user.getRoomUser().getHeight()));
				this.appendInt32(user.getRoomUser().getHeadRotation());
				this.appendInt32(user.getRoomUser().getRotation());

				String status = "/";

				for (Entry<String, String> set : user.getRoomUser().getStatuses().entrySet()) {
					status += set.getKey() + " " + set.getValue() + "/";
				}

				this.appendString(status + "/");
			}
		}
	}

}
