package org.alexdev.icarus.messages.incoming.room.user;

import java.util.LinkedList;

import org.alexdev.icarus.alexpathfinder.AlexPathfinder;
import org.alexdev.icarus.game.room.model.Point;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class UserWalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		int X = request.readInt();
		int Y = request.readInt();

		if (session.getRoomUser().getPoint().sameAs(new Point(X, Y))) {
			return;
		}

		RoomUser roomUser = session.getRoomUser();
		roomUser.createPathfinder();

		roomUser.setGoalX(X);
		roomUser.setGoalY(Y);

		LinkedList<Point> path = null;
		
		//AlexPathfinder pathfinder = new AlexPathfinder(roomUser.getRoom().getCollisionMap());
		//path = pathfinder.findPath(roomUser.getPoint(), roomUser.getGoalPoint());
		path = roomUser.getPathfinder().calculateShortestPath(roomUser.getPoint(), roomUser.getGoalPoint());

		if (path == null || path.size() == 0) { // user selected invalid tile, cannot walk there!
			return;
		}

		roomUser.setPath(path);
		roomUser.setWalking(true);
	}
}
