package net.quackster.icarus.messages.outgoing.user;

import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class LandingWidgetMessageComposer extends Response {
	
	public LandingWidgetMessageComposer() {
		this.init(Outgoing.LandingWidgetMessageComposer);
		this.appendString("");
		this.appendString("");
	}
}
