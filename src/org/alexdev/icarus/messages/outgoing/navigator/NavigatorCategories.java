package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

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
