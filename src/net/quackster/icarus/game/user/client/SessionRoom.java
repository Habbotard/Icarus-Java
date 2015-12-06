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

	public void setRoom(Room room) {
		this.room = room;
	}

	public boolean isLoadingRoom() {
		return isLoadingRoom;
	}

	public void setLoadingRoom(boolean isLoadingRoom) {
		this.isLoadingRoom = isLoadingRoom;
	}
}
