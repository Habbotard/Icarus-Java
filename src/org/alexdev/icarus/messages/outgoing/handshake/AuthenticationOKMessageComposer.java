package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class AuthenticationOKMessageComposer extends Response {

	public AuthenticationOKMessageComposer() {
		this.init(Outgoing.AuthenticationOKMessageComposer);
	}

}
