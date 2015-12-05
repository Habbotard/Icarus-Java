package net.quackster.icarus.messages.outgoing.navigator;

import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class SearchResultSetComposer extends Response {

	public SearchResultSetComposer(NavigatorTab navigatorTab, String searchQuery) {

		this.init(Outgoing.SearchResultSetComposer);
		this.appendString(navigatorTab.getTabName());
		this.appendString(searchQuery);

		if (searchQuery.isEmpty()) {

			List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();
			boolean roomLimit = true;
			
			if (navigatorTab.getChildId() != -1) { // child tab, aka client requested for more room data 
				tabs.add(navigatorTab);
				roomLimit = false;
			} else {
				tabs.addAll(navigatorTab.getChildTabs());
			}
				
			this.appendInt32(tabs.size());
				
			for (NavigatorTab tab : tabs) {
				
				this.appendString(tab.getTabName());
				this.appendString(tab.getTitle());
				this.appendInt32(roomLimit ? (int)tab.getButtonType() : 2); // force no button
				this.appendBoolean(roomLimit ? tab.isClosed() : false); // force collapsed
				this.appendInt32(tab.isThumbnail());
				
				int amount = 2;
				this.appendInt32(amount);

				for (int i = 0; i < amount; i++) {
					new Room("Alex's Room").serialiseNavigatorListing(this, false);
				}
			}

		} else { // TODO: Search
			this.appendInt32(0);
		}

	}

}
