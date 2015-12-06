package net.quackster.icarus.game.room.populator;

import java.util.List;

import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;

public abstract class IRoomPopulator {

	private NavigatorTab navigatorTab;
	public abstract List<Room> generateListing(Session session);

	public NavigatorTab getNavigatorTab() {
		return navigatorTab;
	}
	
	public void setNavigatorTab(NavigatorTab tab) {
		this.navigatorTab = tab;
	}
}
