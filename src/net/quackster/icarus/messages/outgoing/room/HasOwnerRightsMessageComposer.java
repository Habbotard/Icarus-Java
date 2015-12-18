package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class HasOwnerRightsMessageComposer extends Response {

	public HasOwnerRightsMessageComposer() {
		this.init(Outgoing.HasOwnerRightsMessageComposer);
	}
}
