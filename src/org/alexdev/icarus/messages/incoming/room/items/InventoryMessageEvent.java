package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class InventoryMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		if (!session.getInventory().isInitalised()) {
			session.getInventory().load();
		}

	}

}
