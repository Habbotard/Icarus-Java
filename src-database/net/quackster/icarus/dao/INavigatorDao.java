package net.quackster.icarus.dao;

import java.util.List;

import net.quackster.icarus.game.navigator.NavigatorTab;

public interface INavigatorDao {
	public List<NavigatorTab> getTabs(int childId);
}
