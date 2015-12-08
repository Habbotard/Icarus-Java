package net.quackster.icarus.dao.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.user.PlayerDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class RoomDao {

	private static Map<String, RoomModel> roomModels;
	
	public static void loadRoomModels() {
		
		roomModels = new HashMap<String, RoomModel>();
		
		try {
			
			ResultSet Row = Icarus.getStorage().getTable("SELECT * FROM room_models");
			
			while (Row.next()) {
				roomModels.put(Row.getString("id"), new RoomModel(Row.getString("id"), Row.getString("heightmap"), Row.getInt("door_x"), Row.getInt("door_y"), Row.getInt("door_z"), Row.getInt("door_dir")));
			}
			
		} catch (Exception e) {
			Log.exception(e);
		}
		
	}
	
	public static List<Room> getPlayerRooms(CharacterDetails details) {
		return getPlayerRooms(details, false);
	}
	
	public static List<Room> getPlayerRooms(CharacterDetails details, boolean storeInMemory) {

		List<Room> rooms = new ArrayList<Room>();
		ResultSet row = null;

		try {

			row =  Icarus.getStorage().getTable("SELECT * FROM rooms WHERE owner_id = " + details.getId());

			while (row.next()) {

				int id = row.getInt("id");
				
				Room room = Icarus.getGame().getRoomManager().find(id);

				if (room == null) {
					room = new Room(row, details.getUsername());
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

	public static Room getRoom(int roomId) {
		return getRoom(roomId, false);
	}
	
	public static Room getRoom(int roomId, boolean storeInMemory) {

		try {

			ResultSet row =  Icarus.getStorage().getRow("SELECT * FROM rooms WHERE id = " + roomId);
			
			Room room = Icarus.getGame().getRoomManager().find(roomId);

			if (room == null) {
				CharacterDetails details = PlayerDao.getDetails(row.getInt("owner_id"));
				room = new Room(row, details.getUsername());
			}
			
			Storage.releaseObject(row);

			if (storeInMemory) {
				Icarus.getGame().getRoomManager().add(room);
			}

			return room;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			return null;
		}

		return null;
	}


	public static RoomModel getModel(String model) {
		return roomModels.get(model);
	}
	
}
