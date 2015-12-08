package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.pathfinder.Point;

public class UserWalkMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int X = request.readInt();
		int Y = request.readInt();

		try {

			if (session.getRoomUser().getPoint().sameAs(new Point(X, Y))) {
				return;
			}

			SessionRoom roomUser = session.getRoomUser();
			roomUser.createPathfinder();

			roomUser.setGoalX(X);
			roomUser.setGoalY(Y);
			roomUser.setPath(roomUser.getPathfinder().calculateShortestPath(roomUser.getPoint(), roomUser.getGoalPoint()));

			if (roomUser.getPath() == null) { // user selected invalid tile, cannot walk there!
				return;
			}
			
			roomUser.setWalking(true);

		}catch (Exception e) {
			e.printStackTrace();

		}
	}
}
