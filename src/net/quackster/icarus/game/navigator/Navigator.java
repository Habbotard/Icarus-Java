package net.quackster.icarus.game.navigator;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class Navigator {

	private List<NavigatorTab> tabs;
	//private List<NavigatorTab> childTabs;

	public Navigator() {
		
		this.tabs = new ArrayList<NavigatorTab>();
		//this.childTabs = new ArrayList<NavigatorTab>();
	
		try {
			this.tabs = this.getTabs(-1);
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public NavigatorTab getParentTab(String tabName) {

		try {
			return this.tabs.stream().filter(t -> t.getTabName().equals(tabName)).findFirst().get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<NavigatorTab> getParentTabs() {

		try {
			return this.tabs.stream().filter(t -> t.getChildId() == -1).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
	
	public NavigatorTab getChildTab(String tabName) {

		try {
			return this.tabs.stream().filter(t -> (t.getTabName().equals(tabName) && t.getChildId() != -1)).findFirst().get();
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
				tabs.addAll(this.getTabs(tab.getId()));
			}
		}

		Storage.releaseResultSet(row);
		return tabs;
	}

	public List<NavigatorTab> getAllTabs() {
		return this.tabs;
	}
}
