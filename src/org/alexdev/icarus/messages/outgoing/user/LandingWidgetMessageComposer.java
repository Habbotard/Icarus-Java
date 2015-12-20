package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class LandingWidgetMessageComposer extends Response {
	
	public LandingWidgetMessageComposer() {
		this.init(Outgoing.LandingWidgetMessageComposer);
		this.appendString("");
		this.appendString("");
	}
}
