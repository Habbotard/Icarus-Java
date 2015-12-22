package org.alexdev.icarus.dao.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.IFurnitureDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.mysql.Storage;

public class MySQLFurniture implements IFurnitureDao, IProcessStorage<Furniture, ResultSet> {

	private MySQLDao dao;

	public MySQLFurniture(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Furniture> getFurniture() {
		
		List<Furniture> furni = new ArrayList<Furniture>();
		
		ResultSet row = null;
		
		try {
			
			row = this.dao.getStorage().getTable("SELECT * FROM furniture");
			
			while (row.next()) {
				
				Furniture furniture = new Furniture();
				this.fill(furniture, row);
				furni.add(furniture);
			}
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}
		
		return furni;
		
	}

	@Override
	public Furniture fill(Furniture instance, ResultSet row) throws Exception {
		
		instance.fill(row.getInt("id"), row.getString("public_name"), row.getString("item_name"), row.getString("type"), 
				row.getInt("width"), row.getInt("length"), row.getDouble("stack_height"), row.getInt("can_stack") == 1,
				row.getInt("can_sit") == 1, row.getInt("is_walkable") == 1, row.getInt("sprite_id"), row.getInt("allow_recycle") == 1, 
				row.getInt("allow_trade") == 1, row.getInt("allow_marketplace_sell") == 1, row.getInt("allow_gift") == 1, 
				row.getInt("allow_inventory_stack") == 1, InteractionType.getType(row.getString("interaction_type")), row.getInt("interaction_modes_count"),
				row.getString("vending_ids").split(","));
		
		return instance;
	}
}
