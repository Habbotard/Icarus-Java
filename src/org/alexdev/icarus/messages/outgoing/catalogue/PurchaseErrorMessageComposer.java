package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class PurchaseErrorMessageComposer extends Response {

	public PurchaseErrorMessageComposer(boolean creditsError, boolean pixelError) {
		this.init(Outgoing.LackFundsMessageComposer);
		this.appendBoolean(creditsError);
		this.appendBoolean(pixelError);
	}

}
