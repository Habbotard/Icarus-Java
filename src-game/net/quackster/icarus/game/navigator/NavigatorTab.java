package net.quackster.icarus.game.navigator;

import java.util.List;
import java.util.stream.Collectors;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.populator.IRoomPopulator;

public class NavigatorTab {

	private int id;
	private int childId;
	private String tabName;
	private String title;
	private byte buttonType;
	private boolean closed;
	private boolean thumbnail;
	
	private IRoomPopulator roomPopulator;
	
	public NavigatorTab() {
		
	}
	
	public void fill(int id, int childId, String tabName, String title, byte buttonType, boolean closed, boolean thumbnail, String roomPopulator) {
		
		this.id = id;
		this.childId = childId;
		this.tabName = tabName;
		this.title = title;
		this.buttonType = buttonType;
		this.closed = closed;
		this.thumbnail = thumbnail;

		String roomPopulatorClass = roomPopulator;
		
		if (roomPopulator.isEmpty()) {
			return;
		}
		
		try {
			
			Class<? extends IRoomPopulator> clazz = Class.forName("net.quackster.icarus.game.room.populator." + roomPopulatorClass).asSubclass(IRoomPopulator.class);
			this.roomPopulator = clazz.newInstance();
			this.roomPopulator.setNavigatorTab(this);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.roomPopulator = null;
		}
	}


	public List<NavigatorTab> getChildTabs() {

		try {
			return Icarus.getGame().getNavigatorManager().getAllTabs().stream().filter(t -> t.childId == this.id).collect(Collectors.toList());
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

	public IRoomPopulator getRoomPopulator() {
		return roomPopulator;
	}

}
