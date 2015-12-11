package net.quackster.icarus.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.dao.INavigatorDao;
import net.quackster.icarus.game.navigator.NavigatorTab;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLNavigatorDao implements INavigatorDao {

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
	public NavigatorTab fill(NavigatorTab instance, Object data) throws SQLException {
		
		ResultSet set = (ResultSet)data;
		
		instance.fill(set.getInt("id"), set.getInt("child_id"), set.getString("tab_name"), set.getString("title"), set.getByte("button_type"), 
					set.getByte("closed") == 1, set.getByte("thumbnail") == 1, set.getString("room_populator"));
		
		return instance;
	}
}
