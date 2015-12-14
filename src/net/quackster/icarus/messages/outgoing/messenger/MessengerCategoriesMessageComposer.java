package net.quackster.icarus.messages.outgoing.messenger;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.util.GameSettings;

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
