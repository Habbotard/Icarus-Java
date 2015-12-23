package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class InventoryMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		List<Item> items = session.getInventory().getItems();
	}

}
