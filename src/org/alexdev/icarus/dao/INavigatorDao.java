package org.alexdev.icarus.dao;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;

public interface INavigatorDao {
	public List<NavigatorTab> getTabs(int childId);
}
