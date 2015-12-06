package net.quackster.icarus.game.navigator;

import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.mysql.StorageObject;

public class NavigatorTab {

	private int id;
	private int childId;
	private String tabName;
	private String title;
	private byte buttonType;
	private boolean closed;
	private boolean thumbnail;

	public NavigatorTab(ResultSet set) throws Exception {

		this.id = set.getInt("id");
		this.childId = set.getInt("child_id");
		this.tabName = set.getString("tab_name");
		this.title = set.getString("title");
		this.buttonType = set.getByte("button_type");
		this.closed = set.getByte("closed") == 1;
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

	public boolean isClosed() {
		return closed;
	}

	public boolean isThumbnail() {
		return thumbnail;
	}


	//public List<NavigatorTab> getChildTabs() {
	//return childTabs;
	//}
}
