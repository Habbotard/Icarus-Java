package org.alexdev.icarus.game.inventory;

import java.util.List;

import org.alexdev.icarus.game.item.Item;

public class Inventory {

	private boolean initalised;
	private List<Item> items;

	public Inventory() {
		this.initalised = false;
	}

	public void load() {

		if (this.initalised) {
			return;
		}


	}

	public void dispose() {

		if (this.items != null) {
			this.items.clear();
			this.items = null;
		}
	}

	public boolean isInitalised() {
		return initalised;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}
}
