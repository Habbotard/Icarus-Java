package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CatalogueTabMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class CatalogueMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		String type = request.readString();

		session.send(new CatalogueTabMessageComposer(type, -1));
	}

}
