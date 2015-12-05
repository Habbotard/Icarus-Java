package net.quackster.icarus.game.navigator;

import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.mysql.StorageObject;

public class NavigatorTab implements StorageObject {

	private int id;
	private int childId;
	private String tabName;
	private String title;
	private byte buttonType;
	private boolean collapsed;
	private boolean thumbnail;

	//private List<NavigatorTab> childTabs;

	@Override
	public void fill(ResultSet set) throws Exception {

		this.id = set.getInt("id");
		this.childId = set.getInt("child_id");
		this.tabName = set.getString("tab_name");
		this.title = set.getString("title");
		this.buttonType = set.getByte("button_setting");
		this.collapsed = set.getByte("collapsed") == 1;
		this.thumbnail = set.getByte("thumbnail") == 1;
	}


	public List<NavigatorTab> getChildTabs() {

		try {
			return Icarus.getGame().getNavigator().getAllTabs().stream().filter(t -> t.childId == this.id).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getId() {
		return id;
	}

	public int getChildId() {
		return childId;
	}

	public String getTabName() {
		return tabName;
	}

	public String getTitle() {
		return title;
	}

	public byte getButtonType() {
		return buttonType;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public boolean isThumbnail() {
		return thumbnail;
	}


	//public List<NavigatorTab> getChildTabs() {
	//return childTabs;
	//}
}
