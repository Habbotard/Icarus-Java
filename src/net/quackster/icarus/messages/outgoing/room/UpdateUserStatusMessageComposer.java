package net.quackster.icarus.messages.outgoing.room;

import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UpdateUserStatusMessageComposer extends Response {

	public UpdateUserStatusMessageComposer(Session session) {
		ConcurrentLinkedQueue<Session> single = new ConcurrentLinkedQueue<Session>();
		single.add(session);
		this.compose(single);
	}
	
	
	public UpdateUserStatusMessageComposer(ConcurrentLinkedQueue<Session> users) {
		this.compose(users);
	}
	
	public UpdateUserStatusMessageComposer(List<Session> usersToUpdate) {
		ConcurrentLinkedQueue<Session> users = new ConcurrentLinkedQueue<Session>(usersToUpdate);
		this.compose(users);
	}


	public void compose(ConcurrentLinkedQueue<Session> users) {
		
		this.init(Outgoing.UpdateUserStatusMessageComposer);
		this.appendInt32(users.size());
		
		for (Session user : users) {
			
			this.appendInt32(user.getDetails().getId());
			this.appendInt32(user.getRoomUser().getX());
			this.appendInt32(user.getRoomUser().getY());
			this.appendString(Double.toString(user.getRoomUser().getHeight()));
			this.appendInt32(user.getRoomUser().getRotation());
			this.appendInt32(user.getRoomUser().getRotation());

			String Status = "/";

			for (Entry<String, String> set : user.getRoomUser().getStatuses().entrySet())
			{
				Status += set.getKey() + " " + set.getValue() + "/";
			}

			this.appendString(Status + "/");
		}
	}

}
