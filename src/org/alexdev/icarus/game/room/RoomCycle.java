package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.entity.IRoomEntity;
import org.alexdev.icarus.game.room.model.Point;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;

public class RoomCycle implements Runnable {

	//private int tick;
	
	private Room room;
	private List<IEntity> usersToUpdate;

	public RoomCycle (Room room) {
		this.room = room;
		//this.tick = 0;
		this.usersToUpdate = new ArrayList<IEntity>();
	}

	@Override
	public void run() {

		try {

			synchronized (room.getEntities()) { // gotta have dat thread safety, amirite? 

				ConcurrentLinkedQueue<IEntity> users = new ConcurrentLinkedQueue<IEntity>(room.getEntities());

				for (IEntity session : users) {
					
					IRoomEntity roomUser = session.getRoomUser();
					
					/*if (tick % 12 == 0) {
						
						if (session.getType() == EntityType.BOT) {
							
							roomUser.chat("helloz", 2, 1, false, false);
							
							roomUser.setGoalX(Icarus.getUtilities().getRandom().nextInt(10));
							roomUser.setGoalY(Icarus.getUtilities().getRandom().nextInt(10));
							roomUser.createPathfinder();
							
							LinkedList<Point> path = session.getRoomUser().getPathfinder().calculateShortestPath(roomUser.getPoint(), roomUser.getGoalPoint());

							if (path == null) { // user selected invalid tile, cannot walk there!
								continue;
							}
							
							roomUser.setPath(path);
							roomUser.setWalking(true);
						}
					}*/

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

							roomUser.setRotation(Rotation.calculate(roomUser.getX(), roomUser.getY(), next.getX(), next.getY()), false);

							double height = room.getData().getModel().getSquareHeight()[next.getX()][next.getY()];

							roomUser.getStatuses().put("mv", String.valueOf(next.getX()).concat(",").concat(String.valueOf(next.getY())).concat(",").concat(String.valueOf(height)));
							roomUser.updateStatus();

							roomUser.setX(next.getX());
							roomUser.setY(next.getY());
							roomUser.setHeight(height);
						}

					} else if (roomUser.isWalking()) {
						roomUser.stopWalking(true);
					}

					if (roomUser.needsUpdate()) {

						roomUser.stopWalking(false);
						usersToUpdate.add(session);

						if (roomUser.getPoint().sameAs(new Point(room.getData().getModel().getDoorX(), room.getData().getModel().getDoorY()))) {
							//roomUser.getRoom().leaveRoom((Session)roomUser.getEntity(), true);
							//continue;
						}
					}
				}


				if (usersToUpdate.size() > 0) {
					room.send(new UserStatusMessageComposer(usersToUpdate));
					this.usersToUpdate.clear();

					// regenerate map at the end of people walking if the room disallows people walking through each other
					if (!room.getData().isAllowWalkthrough()) {
						room.regenerateCollisionMap();
					}
				}

				users.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		//++tick;
	}
}
