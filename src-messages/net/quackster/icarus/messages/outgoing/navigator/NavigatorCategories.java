package net.quackster.icarus.messages.outgoing.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class NavigatorCategories extends Response {
	
	public NavigatorCategories() {
		
		String[] categories = Icarus.getGame().getNavigatorManager().getPrivateRoomCategories();
		
		this.init(Outgoing.NavigatorCategories);
		this.appendInt32(4 + categories.length);

		for (String category : categories) {
			this.appendString("category__" + category);
		}

		this.appendString("recommended");
		this.appendString("new_ads");
		this.appendString("staffpicks");
		this.appendString("official");
	}
}
