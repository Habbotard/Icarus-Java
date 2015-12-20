package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.navigator.SearchResultSetComposer;
import org.alexdev.icarus.netty.readers.Request;

public class SearchNewNavigatorEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String tab = request.readString();
		String searchQuery = request.readString();
		
		NavigatorTab navigatorTab = Icarus.getGame().getNavigatorManager().getTab(tab);
		
		if (navigatorTab == null) {
			return;
		}
		
		session.send(new SearchResultSetComposer(session, navigatorTab, searchQuery));
	}


		/*if (staticId.equals("my")) {
			response.appendInt32(60);

			for (int i = 0; i < 60; i++) {
				new Room("Alex's Room").serialiseNavigatorListing(response, false);
			}
		}
		else {
			response.appendInt32(0);
		}
	}*/
}
