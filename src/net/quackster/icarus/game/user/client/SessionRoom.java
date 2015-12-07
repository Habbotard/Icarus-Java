package net.quackster.icarus.game.user.client;

import java.util.HashMap;

import net.quackster.icarus.game.room.Room;

public class SessionRoom {

	private boolean inRoom;
	private boolean isLoadingRoom;
	private Room room;
	
	private int X;
	private int Y;
	
	private int GoalX;
	private int GoalY;
	
	private int rotation;
	private double height;
	
	public HashMap<String, String> Statuses;
	private boolean setIsWalking;

	public SessionRoom() {
		this.inRoom = false;
		this.isLoadingRoom = false;
		
		this.rotation = 0;
		this.X = 0;
		this.Y = 0;
		this.GoalX = 0;
		this.GoalY = 0;
		this.Statuses = new HashMap<String, String>();
	}

	public boolean isInRoom() {
		return inRoom;
	}

	public void setInRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}

	public Room getRoom() {
		return room;
	}
	
	public int getRoomId() {
		return (room == null ? -1 : room.getId());
	}

	public void setRoom(Room room) {
		this.room = room;
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
		return Statuses;
	}

	public boolean isWalking() {
		return setIsWalking;
	}

	public void setIsWalking(boolean setIsWalking) {
		this.setIsWalking = setIsWalking;
	}


}
