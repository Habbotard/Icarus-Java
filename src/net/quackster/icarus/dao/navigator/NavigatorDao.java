package net.quackster.icarus.dao.navigator;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class NavigatorDao {

	public static List<NavigatorTab> getTabs(int childId) {

		List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();

		ResultSet row = null;

		try {

			row =  Icarus.getStorage().getTable("SELECT * FROM navigator_tabs WHERE child_id = " + childId);

			while (row.next()) {
				
				NavigatorTab tab = new NavigatorTab(row);
				tabs.add(tab);	
				tabs.addAll(NavigatorDao.getTabs(tab.getId()));
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		return tabs;
	}
}
