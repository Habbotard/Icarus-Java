package net.quackster.icarus.game.room;

import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.messages.outgoing.room.UpdateUserStatusMessageComposer;
import net.quackster.icarus.pathfinder.Point;

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

			synchronized (room.getUsers()) {

				for (Session session : room.getUsers()) {

					SessionRoom roomUser = session.getRoomUser();

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

						int Rot = 0;//Rotation.Calculate(roomUser.getX(), roomUser.getY(), next.X, next.Y);
						roomUser.setRotation(Rot);

						roomUser.getStatuses().put("mv", String.valueOf(next.getX()).concat(",").concat(String.valueOf(next.getY())).concat(",").concat(String.valueOf(room.getModel().getSquareHeight()[next.getX()][next.getY()])));
						roomUser.updateStatus();

						roomUser.setX(next.getX());
						roomUser.setY(next.getY());

						roomUser.setHeight(room.getModel().getSquareHeight()[next.getX()][next.getY()]);


					} else if (roomUser.isWalking()) {

						if (roomUser.getStatuses().containsKey("mv")) {
							roomUser.getStatuses().remove("mv");
						}

						roomUser.setNeedUpdate(true);
						roomUser.setWalking(false);
					}

					if (roomUser.needsUpdate()) {

						if (roomUser.getStatuses().containsKey("mv")) {
							roomUser.getStatuses().remove("mv");
						}

						usersToUpdate.add(session);
						roomUser.setNeedUpdate(false);
						roomUser.setWalking(false);
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
