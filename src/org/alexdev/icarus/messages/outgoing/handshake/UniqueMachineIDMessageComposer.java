package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class UniqueMachineIDMessageComposer extends Response {

	public UniqueMachineIDMessageComposer(String uniqueMachineId) {
		this.init(Outgoing.UniqueMachineIDMessageComposer);
		this.appendString(uniqueMachineId);
	}
}
