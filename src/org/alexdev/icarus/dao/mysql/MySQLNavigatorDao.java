package org.alexdev.icarus.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.INavigatorDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.log.Log;

public class MySQLNavigatorDao implements INavigatorDao, IProcessStorage<NavigatorTab, ResultSet> {

	private MySQLDao dao;

	public MySQLNavigatorDao(MySQLDao dao) {
		this.dao = dao;
	}

	public List<NavigatorTab> getTabs(int childId) {

		List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();

		ResultSet row = null;

		try {

			row =  dao.getStorage().getTable("SELECT * FROM navigator_tabs WHERE child_id = " + childId);

			while (row.next()) {
				
				NavigatorTab tab = new NavigatorTab();
				this.fill(tab, row);
				
				tabs.add(tab);
				tabs.addAll(getTabs(tab.getId()));
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		return tabs;
	}

	@Override
	public NavigatorTab fill(NavigatorTab instance, ResultSet set) throws SQLException {
		
		instance.fill(set.getInt("id"), set.getInt("child_id"), set.getString("tab_name"), 
						set.getString("title"), set.getByte("button_type"), set.getByte("closed") == 1, 
						set.getByte("thumbnail") == 1, set.getString("room_populator"));
		
		return instance;
	}

}
