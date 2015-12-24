package org.alexdev.icarus.game.item;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;

public class Item {

	private int id;
	private int userId;
	private int itemId;
	private int roomId;
	private int x;
	private int y;
	private double z;
	private String extraData;
	
	public void fill(int id, int userId, int itemId, int roomId, int x, int y, double z, String extraData) {
		this.id = id;
		this.userId = userId;
		this.itemId = itemId;
		this.roomId = roomId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.extraData = extraData;
	}

	public int getId() {
		return id;
	}
	
	public Furniture getData() {
		return Icarus.getGame().getFurniture().getFurnitureById(this.itemId);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public int getOwnerId() {
		return userId;
	}
	
	public int getItemId() {
		return itemId;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public boolean isWallItem() {
		return this.getData().getType().equals("i");
	}
	
	public boolean isSongDisk() {
		return this.getData().getInteractionType() == InteractionType.MUSICDISK;
	}
	
}
