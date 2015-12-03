package net.quackster.icarus.messages.outgoing.user;

import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserObjectMessageComposer extends Response {

	public UserObjectMessageComposer(CharacterDetails details) {
		
		this.init(Outgoing.UserObjectMessageComposer);
		this.appendInt32(1); // User ID
		this.appendString("Alex"); // Username
		this.appendString("hr-831-1407.hd-190-14.ch-3015-100.lg-285-82.sh-295-64.fa-1201"); // Figure
		this.appendString("M"); // Gender
		this.appendString("Icarus"); // Motto
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
