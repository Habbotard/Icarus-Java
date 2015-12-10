package net.quackster.icarus.dao;

import java.sql.SQLException;
import java.util.List;

import net.quackster.icarus.game.navigator.NavigatorTab;

public interface INavigatorDao {
	public List<NavigatorTab> getTabs(int childId);
	public NavigatorTab fill(NavigatorTab instance, Object data) throws Exception;
}
