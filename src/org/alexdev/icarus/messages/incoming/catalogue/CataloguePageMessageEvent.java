package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class CataloguePageMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		int pageId = request.readInt();
		
		CataloguePage page = Icarus.getGame().getCatalogue().getPage(pageId);
		
		if (page == null) {
			return;
		}
		
		session.send(page.getComposer());
	}

}
