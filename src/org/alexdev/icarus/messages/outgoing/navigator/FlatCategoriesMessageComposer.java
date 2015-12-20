package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class FlatCategoriesMessageComposer extends Response {

	public FlatCategoriesMessageComposer() {
		
		String[] categories = Icarus.getGame().getNavigatorManager().getPrivateRoomCategories();
		
		this.init(Outgoing.FlatCategoriesMessageComposer);
		this.appendInt32(categories.length);

		int index = 0;
		
		for (String category : categories) {
			this.appendInt32(index);
			this.appendString(category);
			this.appendBoolean(true); // show category?
			this.appendBoolean(false); // no idea
			this.appendString("NONE");
			this.appendString("");
			this.appendBoolean(false);
			
			++index;
		}
	}
}
