package org.alexdev.icarus.dao.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.IInventoryDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.mysql.Storage;

public class MySQLInventoryDao implements IInventoryDao, IProcessStorage<Item, ResultSet> {

	private MySQLDao dao;

	public MySQLInventoryDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Item> getInventory(int userId) {
		
		List<Item> items = new ArrayList<Item>();
		
		ResultSet row = null;
		
		try {
			
			row = this.dao.getStorage().getTable("SELECT * FROM items");
			
			while (row.next()) {
				
				Item item = new Item();
				this.fill(item, row);
				items.add(item);
			}
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}
		
		return items;
	}

	@Override
	public Item fill(Item instance, ResultSet row) throws Exception {
		instance.fill(row.getInt("id"), row.getInt("user_id"), row.getInt("item_id"), row.getInt("room_id"), row.getInt("x"), row.getInt("y"), row.getDouble("z"));
		return null;
	}

}
