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
		this.appendInt32(inventory.getWallItems().size());// + inventory.getFloorItems().size());

		for (Item item : inventory.getWallItems()) {

			this.appendInt32(item.getId());
			this.appendString(item.getData().getType().toUpperCase());
			this.appendInt32(item.getId());
			this.appendInt32(item.getData().getSpriteId());

			if (item.getData().getItemName().contains("landscape"))
				this.appendInt32(4);
			else if (item.getData().getItemName().contains("wallpaper"))
				this.appendInt32(2);
			else if (item.getData().getItemName().contains("a2")) 
				this.appendInt32(3);
			else
				this.appendInt32(1);

			this.appendInt32(0);
			this.appendString(item.getExtraData());
			this.appendBoolean(item.getData().allowRecycle());
			this.appendBoolean(item.getData().allowTrade());
			this.appendBoolean(item.getData().allowInventoryStack());
			this.appendBoolean(item.getData().allowMarketplaceSell());
			this.appendInt32(-1);
			this.appendBoolean(false);
			this.appendInt32(-1);
		}
		
		for (Item item : inventory.getFloorItems()) {
			
			/*this.appendInt32(item.getId());
			this.appendString(item.getData().getType().toUpperCase());
			this.appendInt32(item.getId());
			this.appendInt32(item.getData().getSpriteId());
			this.appendInt32(1);
			this.appendString(item.getExtraData());
			this.appendInt32(0);
			this.appendBoolean(item.getData().allowRecycle());
			this.appendBoolean(item.getData().allowTrade());
			this.appendBoolean(item.getData().allowInventoryStack());
			this.appendBoolean(item.getData().allowMarketplaceSell());
			this.appendInt32(-1);
			this.appendBoolean(false); 
			this.appendInt32(-1);
			
			if (!item.getData().getType().equals("s")) {
				this.appendString("");
				this.appendInt32(0);
			}*/
		}

	}

}
