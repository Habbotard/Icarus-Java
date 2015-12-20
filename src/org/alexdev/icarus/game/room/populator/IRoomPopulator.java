package org.alexdev.icarus.game.room.populator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;

public abstract class IRoomPopulator {

	private NavigatorTab navigatorTab;
	
	public NavigatorTab getNavigatorTab() {
		return navigatorTab;
	}
	
	public void setNavigatorTab(NavigatorTab tab) {
		this.navigatorTab = tab;
	}

	public abstract List<Room> generateListing(boolean limit, Session session);
}
