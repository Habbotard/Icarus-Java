package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

import java.util.LinkedList;

import net.quackster.icarus.game.pathfinder.Point;
import net.quackster.icarus.game.room.RoomUser;

public class UserWalkMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int X = request.readInt();
		int Y = request.readInt();

		try {

			if (session.getRoomUser().getPoint().sameAs(new Point(X, Y))) {
				return;
			}

			RoomUser roomUser = session.getRoomUser();
			roomUser.createPathfinder();

			roomUser.setGoalX(X);
			roomUser.setGoalY(Y);
			
			LinkedList<Point> path = roomUser.getPathfinder().calculateShortestPath(roomUser.getPoint(), roomUser.getGoalPoint());

			if (path == null) { // user selected invalid tile, cannot walk there!
				return;
			}
			
			roomUser.setPath(path);
			roomUser.setWalking(true);

		}catch (Exception e) {
			e.printStackTrace();

		}
	}
}
