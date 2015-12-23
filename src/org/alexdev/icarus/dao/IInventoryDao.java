package org.alexdev.icarus.dao;

import java.util.List;

import org.alexdev.icarus.game.item.Item;

public interface IInventoryDao {
	public List<Item> getInventory(int userId);
}
