package net.quackster.icarus.messages.outgoing.handshake;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class AuthenticationOKMessageComposer extends Response {

	public AuthenticationOKMessageComposer() {
		this.init(Outgoing.AuthenticationOKMessageComposer);
	}
}
