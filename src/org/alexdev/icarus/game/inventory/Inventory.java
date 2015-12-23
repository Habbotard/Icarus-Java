package org.alexdev.icarus.game.inventory;

public class Inventory {

	private boolean initalised;

	public Inventory() {
		this.initalised = false;
	}
	
	public void load() {
		
		if (this.initalised) {
			return;
		}
		
		
	}
	
	public boolean isInitalised() {
		return initalised;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}
}
