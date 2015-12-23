package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class InventoryLoadMessageComposer extends Response {

	public InventoryLoadMessageComposer(int count, Inventory inventory) {
		this.init(Outgoing.InventoryLoadMessageComposer);
		this.appendInt32(1);
		this.appendInt32(0);
		this.appendInt32(count);

		for (Item item : inventory.getFloorItems()) {


		}


		for (Item item : inventory.getWallItems()) {


		}

		for (Item item : inventory.getSongDisks()) {


		}
	}

}
