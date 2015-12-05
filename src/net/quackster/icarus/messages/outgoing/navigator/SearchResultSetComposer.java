package net.quackster.icarus.messages.outgoing.navigator;

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
			
	        this.appendInt32(navigatorTab.getChildTabs().size());
	        
	        for (NavigatorTab childTab : navigatorTab.getChildTabs()) {
	      	
				this.appendString(childTab.getTabName());
				this.appendString(childTab.getTitle());
				this.appendInt32((int)childTab.getButtonType());
				this.appendBoolean(childTab.isCollapsed());
				this.appendInt32(childTab.isThumbnail());
	        	

				int amount = 100;
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
