package net.quackster.icarus.game.entity;

import java.util.HashMap;
import java.util.LinkedList;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.pathfinder.AreaMap;
import net.quackster.icarus.game.pathfinder.Pathfinder;
import net.quackster.icarus.game.pathfinder.heuristics.AStarHeuristic;
import net.quackster.icarus.game.pathfinder.heuristics.ClosestHeuristic;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.model.Point;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.room.notify.FloodFilterMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.TalkMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import net.quackster.icarus.util.GameSettings;

public abstract class IRoomEntity {

	private int virtualId;
	private int lastChatId;
	private int danceId;

	private int X;
	private int Y;
	private double height;

	private int goalX;
	private int goalY;

	private int rotation;
	private int headRotation;

	private HashMap<String, String> statuses;
	private LinkedList<Point> path;

	private Room room;
	private Pathfinder pathfinder;
	private RoomModel model;

	private boolean isWalking;
	private boolean needsUpdate;

	private IEntity entity;

	private long chatFloodTimer;
	private int chatCount;

	public IRoomEntity(IEntity entity) {
		this.dispose();
		this.entity = entity;
	}

	public void createPathfinder() {

		AreaMap map = new AreaMap(this.model, this.room.getCollisionMap());
		AStarHeuristic heuristic = new ClosestHeuristic();

		this.setPathfinder(new Pathfinder(map, heuristic));//new DiagonalHeuristic());
	}

	public void chat(String message, int bubble, int count, boolean shout, boolean spamCheck) {

		boolean isStaff = false;
		Session session = null;

		if (this.entity instanceof Session) {

			session = (Session)this.entity;
			isStaff = session.getDetails().hasFuse("moderator");
		}

		// if current time less than the chat flood timer (last chat time + seconds to check)
		// say that they still need to wait before shouting again
		if (spamCheck) {
			if (Icarus.getUtilities().getTimestamp() < this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD) {

				if (!isStaff) {
					if (session != null) {
						session.send(new FloodFilterMessageComposer(GameSettings.CHAT_FLOOD_WAIT));
					}
					return;
				}
			}
		}

		// TODO: Check if not bot
		// The below function validates the chat bubbles
		if (bubble == 2 || (bubble == 23 && !session.getDetails().hasFuse("moderator")) || bubble < 0 || bubble > 29) {
			bubble = this.lastChatId;
		}

		this.room.send(new TalkMessageComposer(this, shout, message, count, bubble));

		// if the users timestamp has passed the check but the chat count is still high
		// the chat count is reset then

		if (spamCheck) {
			if (!session.getDetails().hasFuse("moderator")) {

				if (Icarus.getUtilities().getTimestamp() > this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD) {
					this.chatCount = 0;
				} else {
					this.chatCount = this.chatCount + 1;
				}

				this.chatFloodTimer = (Icarus.getUtilities().getTimestamp() + GameSettings.CHAT_FLOOD_SECONDS);

			}
		}
	}

	public void dispose() {

		if (this.statuses != null) {
			this.statuses.clear();
		}

		if (this.path != null) {
			this.path.clear();
		}

		this.statuses = null;
		this.path = null;

		this.statuses = new HashMap<String, String>();
		this.path = new LinkedList<Point>();

		this.goalX = -1;
		this.goalY = -1;
		this.lastChatId = 0;
		this.virtualId = -1;
		this.danceId = 0;

	}

	public void stopWalking(boolean needsUpdate) {

		if (this.statuses.containsKey("mv")) {
			this.statuses.remove("mv");
		}

		this.needsUpdate = needsUpdate;
		this.isWalking = false;
		this.setPath(null);

	}

	public void updateStatus() {
		this.room.send(new UserStatusMessageComposer(this.entity));
	}

	public boolean isDancing() {
		return this.danceId != 0;
	}
	public int getVirtualId() {
		return virtualId;
	}

	public void setVirtualId(int virtualId) {
		this.virtualId = virtualId;
	}

	public int getLastChatId() {
		return lastChatId;
	}

	public void setLastChatId(int lastChatId) {
		this.lastChatId = lastChatId;
	}

	public int getDanceId() {
		return danceId;
	}

	public void setDanceId(int danceId) {
		this.danceId = danceId;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getGoalX() {
		return goalX;
	}

	public void setGoalX(int goalX) {
		this.goalX = goalX;
	}

	public int getGoalY() {
		return goalY;
	}

	public void setGoalY(int goalY) {
		this.goalY = goalY;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getHeadRotation() {
		return headRotation;
	}

	public void setRotation(int rotation, boolean headOnly) {

		this.headRotation = rotation;

		if (!headOnly) {
			this.rotation = rotation;
		}
	}

	public HashMap<String, String> getStatuses() {
		return statuses;
	}

	public LinkedList<Point> getPath() {
		return path;
	}


	public void setPath(LinkedList<Point> path) {

		if (this.path != null) {

			this.path.clear();
			this.path = null;
		}

		this.path = path;
	}

	public boolean needsUpdate() {
		return needsUpdate;
	}

	public void setNeedUpdate(boolean needsWalkUpdate) {
		this.needsUpdate = needsWalkUpdate;

		if (!this.needsUpdate) {
			this.goalX = -1;
			this.goalY = -1;
		}
	}

	public Room getRoom() {
		return room;
	}

	public int getRoomId() {
		return (room == null ? 0 : room.getData().getId());
	}

	public void setRoom(Room room) {
		this.room = room;

		if (room != null) {
			this.model = room.getData().getModel();
		}
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(Pathfinder pathfinder) {
		this.pathfinder = pathfinder;
	}

	public RoomModel getModel() {
		return room.getData().getModel();
	}

	public void setModel(RoomModel model) {
		this.model = model;
	}

	public boolean isWalking() {
		return isWalking;
	}

	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	public IEntity getEntity() {
		return entity;
	}

	public Point getPoint() {
		return new Point(this.X, this.Y);
	}

	public Point getGoalPoint() {
		return new Point(this.goalX, this.goalY);
	}

}
