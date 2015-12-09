package net.quackster.icarus.game.user.client;

import java.util.HashMap;
import java.util.LinkedList;

import net.quackster.icarus.game.pathfinder.AreaMap;
import net.quackster.icarus.game.pathfinder.Pathfinder;
import net.quackster.icarus.game.pathfinder.Point;
import net.quackster.icarus.game.pathfinder.heuristics.AStarHeuristic;
import net.quackster.icarus.game.pathfinder.heuristics.ClosestHeuristic;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.room.user.UpdateUserStatusMessageComposer;

public class SessionRoom {

	private boolean inRoom;
	private boolean isLoadingRoom;

	private int X;
	private int Y;

	private int GoalX;
	private int GoalY;

	private int rotation;
	private double height;

	private HashMap<String, String> statuses;
	private LinkedList<Point> path;

	private boolean isWalking;
	private boolean needsUpdate;

	private Room room;
	private Session session;
	private Pathfinder pathfinder;
	private RoomModel model;

	public SessionRoom(Session session) {

		this.session = session;
		this.inRoom = false;
		this.isLoadingRoom = false;
		this.needsUpdate = false;

		this.rotation = 0;
		this.X = 0;
		this.Y = 0;
		this.GoalX = 0;
		this.GoalY = 0;

		this.statuses = new HashMap<String, String>();
		this.path = new LinkedList<Point>();
	}

	public void createPathfinder() {

		AreaMap map = new AreaMap(this.model, this.room.regenerateCollisionMap());
		AStarHeuristic heuristic = new ClosestHeuristic();

		this.pathfinder = new Pathfinder(map, heuristic);//new DiagonalHeuristic());
	}


	public void stopWalking(boolean needsUpdate) {

		if (this.statuses.containsKey("mv")) {
			this.statuses.remove("mv");
		}

		this.needsUpdate = needsUpdate;
		this.isWalking = false;

	}

	public void updateStatus() {
		this.room.send(new UpdateUserStatusMessageComposer(session));
	}


	public void dispose() {

		this.room = null;
		this.pathfinder = null;
		this.session = null;

		this.statuses.clear();
		this.statuses = null;

	}

	public boolean inRoom() {
		return room != null;
	}

	public void setInRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}

	public Room getRoom() {
		return room;
	}

	public RoomModel getModel() {
		return model;
	}

	public int getRoomId() {
		return (room == null ? -1 : room.getId());
	}

	public void setRoom(Room room) {
		this.room = room;

		if (room != null) {
			this.model = room.getModel();
		}
	}

	public boolean isLoadingRoom() {
		return isLoadingRoom;
	}

	public void setLoadingRoom(boolean isLoadingRoom) {
		this.isLoadingRoom = isLoadingRoom;
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

	public Point getPoint() {
		return new Point(this.X, this.Y);
	}

	public int getGoalX() {
		return GoalX;
	}

	public void setGoalX(int goalX) {
		GoalX = goalX;
	}

	public int getGoalY() {
		return GoalY;
	}

	public void setGoalY(int goalY) {
		GoalY = goalY;
	}

	public Point getGoalPoint() {
		return new Point(this.GoalX, this.GoalY);
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public HashMap<String, String> getStatuses() {
		return statuses;
	}

	public boolean isWalking() {
		return isWalking;
	}

	public void setWalking(boolean setIsWalking) {
		this.isWalking = setIsWalking;
	}

	public boolean needsUpdate() {
		return needsUpdate;
	}

	public void setNeedUpdate(boolean needsWalkUpdate) {
		this.needsUpdate = needsWalkUpdate;

		if (!this.needsUpdate) {
			this.GoalX = -1;
			this.GoalY = -1;
		}
	}

	public LinkedList<Point> getPath() {
		return path;
	}

	public void setPath(LinkedList<Point> path) {
		this.path = path;
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public void setPathfinder(Pathfinder pathfinder) {
		this.pathfinder = pathfinder;
	}

	public Session getSession() {
		return this.session;
	}
}
