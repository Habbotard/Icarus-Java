package org.alexdev.icarus.game.item;

public class Item {

	private int id;
	private int userId;
	private int itemId;
	private int roomId;
	private int x;
	private int y;
	private double z;
	
	public void fill(int id, int userId, int itemId, int roomId, int x, int y, double z) {
		this.id = id;
		this.userId = userId;
		this.itemId = itemId;
		this.roomId = roomId;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getId() {
		return id;
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
	
	
}
