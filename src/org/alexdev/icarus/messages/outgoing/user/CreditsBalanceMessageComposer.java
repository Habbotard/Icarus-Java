package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CreditsBalanceMessageComposer extends Response {

	public CreditsBalanceMessageComposer(int credits) {
		this.init(Outgoing.CreditsBalanceMessageComposer);
		this.appendString(credits + ".0");
	}

}
