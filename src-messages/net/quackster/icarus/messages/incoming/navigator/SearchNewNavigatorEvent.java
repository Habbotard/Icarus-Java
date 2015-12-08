package net.quackster.icarus.messages.incoming.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.navigator.SearchResultSetComposer;
import net.quackster.icarus.netty.readers.Request;

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
