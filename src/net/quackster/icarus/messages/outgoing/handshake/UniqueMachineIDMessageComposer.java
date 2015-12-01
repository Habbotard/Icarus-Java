package net.quackster.icarus.messages.outgoing.handshake;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UniqueMachineIDMessageComposer extends Response {

	public UniqueMachineIDMessageComposer(String uniqueMachineId) {
		this.init(Outgoing.UniqueMachineIDMessageComposer);
		this.appendString(uniqueMachineId);
	}
}
