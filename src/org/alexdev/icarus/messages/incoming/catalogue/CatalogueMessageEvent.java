package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CatalogueTabMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class CatalogueMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
	
		String type = request.readString();
		
		if (type == null) {
			return;
		}
		
		List<CatalogueTab> parentTabs = Icarus.getGame().getCatalogue().getParentTabs(session.getDetails().getId());
		
		if (parentTabs == null) {
			return;
		}
		
		session.send(new CatalogueTabMessageComposer(type, parentTabs, -1, session.getDetails().getRank()));
	}

}
