package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.item.InventoryLoadMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class InventoryMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		Inventory inventory = session.getInventory();
		
		if (inventory == null) {
			return;
		}
		
		int count = inventory.getFloorItems().size() + inventory.getWallItems().size();
		
		session.send(new InventoryLoadMessageComposer(count, inventory));
		
		System.out.println("items?!!");
	}

}
