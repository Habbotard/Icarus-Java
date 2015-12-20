package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;
import org.alexdev.icarus.util.GameSettings;

public class MessengerCategoriesMessageComposer extends Response {

	public MessengerCategoriesMessageComposer() {
		
		this.init(Outgoing.MessengerCategoriesMessageComposer);
		this.appendInt32(GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
		this.appendInt32(GameSettings.MAX_FRIENDS_DEFAULT);
		this.appendInt32(GameSettings.MAX_FRIENDS_BASIC);
		this.appendInt32(GameSettings.MAX_FRIENDS_VIP);
		this.appendInt32(0);
	}
}
