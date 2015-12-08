package net.quackster.icarus.messages.outgoing.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class NavigatorMetaDataComposer extends Response {

	public NavigatorMetaDataComposer() {
		
		this.init(Outgoing.NavigatorMetaDataComposer);
		this.appendInt32(Icarus.getGame().getNavigatorManager().getParentTabs().size());

		for (NavigatorTab tab : Icarus.getGame().getNavigatorManager().getParentTabs()) {
			this.appendString(tab.getTabName());
			this.appendInt32(0);
		}
	}
}
