package net.quackster.icarus.game.room;

public enum RoomState {
	
	OPEN(0),
	DOORBELL(1),
	PASSWORD(2);
	
	private int state;

	RoomState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}
}
