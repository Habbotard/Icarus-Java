package net.quackster.icarus.game.user.client;

import net.quackster.icarus.game.room.Room;

public class SessionRoom {

	private boolean inRoom;
	private boolean isLoadingRoom;
	private Room room;

	public SessionRoom() {
		this.inRoom = false;
		this.isLoadingRoom = false;
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

	public Integer getX() {
		return room.getModel().getDoorX();
	}
	
	public Integer getY() {
		return room.getModel().getDoorY();
	}

	public double getHeight() {
		return room.getModel().getSquareHeight()[this.getX()][this.getY()];
	}

	public Integer getBodyRotation() {
		return room.getModel().getDoorRot();
	}
}
