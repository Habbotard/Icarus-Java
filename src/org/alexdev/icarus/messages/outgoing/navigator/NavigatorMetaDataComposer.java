package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

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
