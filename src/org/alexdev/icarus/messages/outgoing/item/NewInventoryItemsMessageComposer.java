package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class NewInventoryItemsMessageComposer extends Response {

	public NewInventoryItemsMessageComposer(List<Item> items) {
		
		this.init(Outgoing.NewInventoryItemsMessageComposer);
		this.appendInt32(1);
		this.appendInt32(items.size());
		
		for (Item bought : items) {	
			this.appendInt32(1);
			this.appendInt32(bought.getId());
		}
	}
}
