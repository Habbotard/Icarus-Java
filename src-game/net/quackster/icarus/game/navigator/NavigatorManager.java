package net.quackster.icarus.game.navigator;

import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.mysql.MySQLNavigatorDao;

public class NavigatorManager {

	private List<NavigatorTab> tabs;
	
	public NavigatorManager() throws Exception {
		this.tabs = Icarus.getDao().getNavigator().getTabs(-1);
	}
	
	public NavigatorTab getTab(String tabName) {

		try {
			return this.tabs.stream().filter(tab -> tab.getTabName().equals(tabName)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<NavigatorTab> getParentTabs() {

		try {
			return this.tabs.stream().filter(tab -> tab.getChildId() == -1).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
	
	public String[] getPrivateRoomCategories() {
		return new String[] { "No Category", "School, Daycare & Adoption Rooms", "Help Centre, Guide & Service Rooms", "Hair Salons & Modelling Rooms", "Gaming & Race Rooms", "Trading & Shopping Rooms", "Maze & Theme Park Rooms", "Chat, Chill & Discussion Rooms", "Club & Group Rooms", "Restaurant, Bar & Night Club Rooms", "Themed & RPG Rooms", "Habbo Staff Rooms" };
	}

	public List<NavigatorTab> getAllTabs() {
		return this.tabs;
	}
}
