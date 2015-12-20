package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class UserStatusMessageComposer extends Response {

	public UserStatusMessageComposer(IEntity entity) {
		this.compose(Arrays.asList(new IEntity[] { entity }));
	}


	public UserStatusMessageComposer(List<IEntity> users) {
		this.compose(users);
	}


	public void compose(List<IEntity> users) {

		this.init(Outgoing.UserStatusMessageComposer);
		
		synchronized (users) {

			this.appendInt32(users.size());

			for (IEntity  user : users) {

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
