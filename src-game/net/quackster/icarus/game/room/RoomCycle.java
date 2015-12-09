package net.quackster.icarus.game.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.quackster.icarus.game.room.models.Rotation;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.messages.outgoing.room.user.UpdateUserStatusMessageComposer;
import net.quackster.icarus.game.pathfinder.Point;

public class RoomCycle implements Runnable {

	private Room room;
	private List<Session> usersToUpdate;

	public RoomCycle (Room room) {
		this.room = room;
		this.usersToUpdate = new ArrayList<Session>();
	}

	@Override
	public void run() {

		try {

			synchronized (room.getUsers()) { // gotta have dat thread safety, amirite? 

				for (Session session : room.getUsers()) {

					SessionRoom roomUser = session.getRoomUser();

					if (roomUser.getPath() == null) { 
						continue;
					}

					if (roomUser.getPoint().sameAs(roomUser.getGoalPoint())) {
						roomUser.setPath(new LinkedList<Point>());
						roomUser.stopWalking(true);
					}

					if (roomUser.getPath() != null) {
						if (roomUser.isWalking() && roomUser.getPath().size() > 0) {

							Point next = roomUser.getPath().poll();

							if (roomUser.getStatuses().containsKey("mv")) {
								roomUser.getStatuses().remove("mv");
							}

							if (roomUser.getStatuses().containsKey("sit")) {
								roomUser.getStatuses().remove("sit");
							}

							if (roomUser.getStatuses().containsKey("lay")) {
								roomUser.getStatuses().remove("lay");
							}

							roomUser.setRotation(Rotation.calculate(roomUser.getX(), roomUser.getY(), next.getX(), next.getY()));

							roomUser.getStatuses().put("mv", String.valueOf(next.getX()).concat(",").concat(String.valueOf(next.getY())).concat(",").concat(String.valueOf(room.getModel().getSquareHeight()[next.getX()][next.getY()])));
							roomUser.updateStatus();

							roomUser.setX(next.getX());
							roomUser.setY(next.getY());

							roomUser.setHeight(room.getModel().getSquareHeight()[next.getX()][next.getY()]);
						}

					} else if (roomUser.isWalking()) {
						roomUser.stopWalking(true);
					}

					if (roomUser.needsUpdate()) {

						roomUser.stopWalking(false);
						usersToUpdate.add(session);

						if (roomUser.getPoint().sameAs(new Point(room.getModel().getDoorX(), room.getModel().getDoorY()))) {
							roomUser.getRoom().leaveRoom(session, true);
							continue;
						}
					}
				}
			}

			if (usersToUpdate.size() > 0) {
				room.send(new UpdateUserStatusMessageComposer(usersToUpdate));
				this.usersToUpdate.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
