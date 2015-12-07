package net.quackster.icarus.messages.outgoing.user;

import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserObjectMessageComposer extends Response {

	public UserObjectMessageComposer(CharacterDetails details) {
		
		this.init(Outgoing.UserObjectMessageComposer);
		this.appendInt32(1); // User ID
		this.appendString(details.getUsername()); // Username
		this.appendString(details.getFigure()); // Figure
		this.appendString("M"); // Gender
		this.appendString(details.getMotto()); // Motto
		this.appendString(""); // ?
		this.appendBoolean(false); // ?
		this.appendInt32(0); // Respect
		this.appendInt32(3); // Daily Respect Points
		this.appendInt32(3); // Daily Pet Respect Points
		this.appendBoolean(true); // ?
		this.appendString("1448526834"); // Last Online (format?)
		this.appendBoolean(true); // Can Change Username
		this.appendBoolean(false); // ?

	}

}
