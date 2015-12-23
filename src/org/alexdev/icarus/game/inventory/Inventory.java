package org.alexdev.icarus.game.inventory;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.user.Session;

public class Inventory {

	private boolean initalised;
	private Session session;
	private List<Item> items;

	public Inventory(Session session) {
		this.initalised = false;
		this.session = session;
	}
	
	public void init() {
		if (!this.initalised) {
			this.items = Icarus.getDao().getInventory().getInventoryItems(this.session.getDetails().getId());
			this.initalised = true;
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

	public List<Item> getItems() {
		return items;
	}

	public List<Item> getFloorItems() {
		return items.stream().filter(item -> !item.isWallItem() && !item.isSongDisk()).collect(Collectors.toList());
	}
	
	public List<Item> getWallItems() {
		return items.stream().filter(item -> item.isWallItem() && !item.isSongDisk()).collect(Collectors.toList());
	}
	
	public List<Item> getSongDisks() {
		return items.stream().filter(item -> item.isSongDisk()).collect(Collectors.toList());
	}
}
