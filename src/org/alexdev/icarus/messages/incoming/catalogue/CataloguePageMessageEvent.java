package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePageMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class CataloguePageMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		int pageId = request.readInt();
		request.readInt();
		String type = request.readString();
		
		CataloguePage page = Icarus.getGame().getCatalogue().getPage(pageId);
		
		if (page == null) {
			return;
		}
		
		session.send(new CataloguePageMessageComposer(page, type));
	}

}
