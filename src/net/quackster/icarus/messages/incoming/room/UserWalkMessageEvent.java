package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class UserWalkMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int X = request.readInt();
		int Y = request.readInt();
		
		session.getRoomUser().setGoalX(X);
		session.getRoomUser().setGoalY(Y);

		try {
			
			if (X == session.getRoomUser().getX() && Y == session.getRoomUser().getY()) {
				return;
			}


			//session.getRoomUser().setPath(path);
			session.getRoomUser().setIsWalking(true);

		} catch (Exception e) {
			Log.exception(e);
		}
	}

}
