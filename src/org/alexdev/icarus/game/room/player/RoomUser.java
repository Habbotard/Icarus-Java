package org.alexdev.icarus.game.room.player;

import org.alexdev.icarus.game.entity.IRoomEntity;
import org.alexdev.icarus.game.user.Session;

public class RoomUser extends IRoomEntity {

	private boolean inRoom;
	private boolean isLoadingRoom;

	public RoomUser(Session session) {
		super(session);
		this.reset();
	}

	public void reset() {
		
		super.dispose();
		this.isLoadingRoom = false;
		this.inRoom = false;
	}

	public void dispose() {
		super.dispose();
	}

	public boolean inRoom() {
		return inRoom && !this.isLoadingRoom; // player is actually inside the room and not busy loading it
	}

	public void setInRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}

	public boolean isLoadingRoom() {
		return isLoadingRoom;
	}

	public void setLoadingRoom(boolean isLoadingRoom) {
		this.isLoadingRoom = isLoadingRoom;
	}
}
