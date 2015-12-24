package org.alexdev.icarus.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.IInventoryDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.log.Log;

public class MySQLInventoryDao implements IInventoryDao, IProcessStorage<Item, ResultSet> {

	private MySQLDao dao;

	public MySQLInventoryDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Item> getInventoryItems(int userId) {
		
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
	public Item getItem(int itemId) {

		try {

			PreparedStatement statement = dao.getStorage().prepare("SELECT * FROM items WHERE id = ? LIMIT 1");
			statement.setInt(1, itemId);

			ResultSet row = statement.executeQuery();

			Item item = new Item();

			if (!row.next()) {
				return null;
			}

			this.fill(item, row);

			Storage.releaseObject(row);

			return item;

		} catch (Exception e) {
			Log.exception(e);
		}

		return null;
	}

	@Override
	public Item newItem(int itemId, int ownerId, String extraData) {


		ResultSet row = null;
		
		try {
			
			PreparedStatement statement = dao.getStorage().prepare("INSERT INTO items (user_id, item_id, extra_data) VALUES(?, ?, ?)", true); {
				statement.setInt(1, ownerId);
				statement.setInt(2, itemId);
				statement.setString(3, extraData);
				statement.executeUpdate();
			}
			
			row = statement.getGeneratedKeys();
			
			if (row != null && row.next()) {
				return this.getItem(row.getInt(1));
			}
			
		} catch (SQLException e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		
		return null;
	}
	
	@Override
	public Item fill(Item instance, ResultSet row) throws Exception {
		instance.fill(row.getInt("id"), row.getInt("user_id"), row.getInt("item_id"), row.getInt("room_id"), row.getInt("x"), row.getInt("y"), row.getDouble("z"), "");
		return null;
	}

}
