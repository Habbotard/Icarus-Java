package org.alexdev.icarus.dao;

import java.util.List;

import org.alexdev.icarus.game.item.Item;

public interface IInventoryDao {
	
	public List<Item> getInventoryItems(int userId);
	public Item newItem(int itemId, int ownerId);
	public Item getItem(int itemsId);
}
