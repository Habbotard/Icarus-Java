package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class HasOwnerRightsMessageComposer extends Response {

	public HasOwnerRightsMessageComposer() {
		this.init(Outgoing.HasOwnerRightsMessageComposer);
	}
}
