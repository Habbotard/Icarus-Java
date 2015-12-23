package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class UpdateInventoryMessageComposer extends Response {

	public UpdateInventoryMessageComposer() {
		this.init(Outgoing.UpdateInventoryMessageComposer);
		
	}
}
