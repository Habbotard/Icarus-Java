package net.quackster.icarus.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.IRoomDao;
import net.quackster.icarus.dao.util.IProcessStorage;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLRoomDao implements IRoomDao, IProcessStorage<Room, ResultSet> {

	private MySQLDao dao;
	private Map<String, RoomModel> roomModels;
	
	public MySQLRoomDao(MySQLDao dao) {
		
		this.dao = dao;
		this.roomModels = new HashMap<String, RoomModel>();
		
		try {
			
			ResultSet Row = dao.getStorage().getTable("SELECT * FROM room_models");
			
			while (Row.next()) {
				roomModels.put(Row.getString("id"), new RoomModel(Row.getString("id"), Row.getString("heightmap"), Row.getInt("door_x"), Row.getInt("door_y"), Row.getInt("door_z"), Row.getInt("door_dir")));
			}
			
		} catch (Exception e) {
			Log.exception(e);
		}
	}
	

	@Override
	public List<Room> getPlayerRooms(CharacterDetails details) {
		return getPlayerRooms(details, false);
	}
	
	@Override
	public List<Room> getPlayerRooms(CharacterDetails details, boolean storeInMemory) {

		List<Room> rooms = new ArrayList<Room>();
		ResultSet row = null;

		try {

			row =  dao.getStorage().getTable("SELECT * FROM rooms WHERE owner_id = " + details.getId());

			while (row.next()) {

				int id = row.getInt("id");
				
				Room room = Icarus.getGame().getRoomManager().find(id);

				if (room == null) {
					room = new Room();
					this.fill(room, row);
				}
				
				rooms.add(room);

				if (storeInMemory) {
					Icarus.getGame().getRoomManager().add(room);
				}
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		return rooms;
	}

	@Override
	public Room getRoom(int roomId) {
		return getRoom(roomId, false);
	}
	
	@Override
	public Room getRoom(int roomId, boolean storeInMemory) {

		try {

			ResultSet row =  dao.getStorage().getRow("SELECT * FROM rooms WHERE id = " + roomId);
			
			Room room = Icarus.getGame().getRoomManager().find(roomId);

			if (room == null) {
				room = new Room();
				this.fill(room, row);
			}
			
			Storage.releaseObject(row);

			if (storeInMemory) {
				Icarus.getGame().getRoomManager().add(room);
			}

			return room;

		} catch (SQLException e) {
			Log.exception(e);
		} catch (NoSuchElementException e) {
			return null;
		}

		return null;
	}

	@Override
	public RoomModel getModel(String model) {
		return roomModels.get(model);
	}

	@Override
	public Room fill(Room instance, ResultSet row) throws SQLException {
		
		CharacterDetails details = Icarus.getDao().getPlayer().getDetails(row.getInt("owner_id"));
		
		
		instance.fill(row.getInt("id"), row.getInt("owner_id"), details.getUsername(), row.getString("name"), row.getInt("state"), row.getInt("users_now"),
						row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
						row.getInt("category"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
						row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
						row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"));
		
		return null;
	}


	
}
