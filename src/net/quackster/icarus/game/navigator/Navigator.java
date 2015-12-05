package net.quackster.icarus.game.navigator;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import net.quackster.icarus.Icarus;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class Navigator {

	private List<NavigatorTab> parentTabs;
	private List<NavigatorTab> childTabs;

	public Navigator() {
		
		this.parentTabs = new ArrayList<NavigatorTab>();
		this.childTabs = new ArrayList<NavigatorTab>();
	
		try {
			this.parentTabs = this.getTabs(-1);
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public NavigatorTab getParentTab(String tabName) {

		try {
			return this.parentTabs.stream().filter(t -> t.getTabName().equals(tabName)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public NavigatorTab getChildTab(String tabName) {

		try {
			return this.childTabs.stream().filter(t -> t.getTabName().equals(tabName)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<NavigatorTab> getTabs(int id) throws Exception {

		List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();
		ResultSet row = Icarus.getStorage().getTable("SELECT * FROM navigator_tabs WHERE child_id = " + id);

		while (row.next()) {
			NavigatorTab tab = new NavigatorTab();
			tab.fill(row);
			tabs.add(tab);
			
			if (tab.getChildId() == -1) {
				childTabs.addAll(this.getTabs(tab.getId()));
			}
		}

		Storage.releaseResultSet(row);
		return tabs;
	}

	public List<NavigatorTab> getParentTabs() {
		return this.parentTabs;
	}

	public List<NavigatorTab> getChildTabs() {
		return this.childTabs;
	}
}
