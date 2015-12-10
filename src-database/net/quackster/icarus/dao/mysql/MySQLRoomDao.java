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
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLRoomDao implements IRoomDao {

	private Map<String, RoomModel> roomModels;
	
	public MySQLRoomDao() {
		
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
	
	@Override
	public List<Room> getPlayerRooms(CharacterDetails details) {
		return getPlayerRooms(details, false);
	}
	
	@Override
	public List<Room> getPlayerRooms(CharacterDetails details, boolean storeInMemory) {

		List<Room> rooms = new ArrayList<Room>();
		ResultSet row = null;

		try {

			row =  Icarus.getStorage().getTable("SELECT * FROM rooms WHERE owner_id = " + details.getId());

			while (row.next()) {

				int id = row.getInt("id");
				
				Room room = Icarus.getGame().getRoomManager().find(id);

				if (room == null) {
					room = new Room();
					this.fill(room, details.getUsername(), row);
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

			ResultSet row =  Icarus.getStorage().getRow("SELECT * FROM rooms WHERE id = " + roomId);
			
			Room room = Icarus.getGame().getRoomManager().find(roomId);

			if (room == null) {
				CharacterDetails details = Icarus.getDao().getPlayer().getDetails(row.getInt("owner_id"));
				//room = new Room(row, details.getUsername());
				this.fill(room, details.getUsername(), row);
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

	@Override
	public RoomModel getModel(String model) {
		return roomModels.get(model);
	}

	@Override
	public Room fill(Room instance, String ownerName, Object data) throws SQLException {
		
		/*(int id, int ownerId, String ownerName, String name, int state, int usersNow, int usersMax,
			String description, int tradeState, int score, int category, int groupId, String model, String wall,
			String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
			boolean hideWall, int wallThickness, int floorThickness, String tagFormat) {*/
		
		/*this.id = row.getInt("id");
		this.ownerId = row.getInt("owner_id");
		this.ownerName = ownerName;
		this.groupId = row.getInt("group_id");
		this.name = row.getString("name");
		this.description = row.getString("description");
		this.state = row.getInt("state");
		this.tradeState = row.getInt("trade_state");
		this.model = row.getString("model");
		this.wall = row.getString("wallpaper");
		this.floor = row.getString("floor");
		this.landscape = row.getString("outside");
		this.usersNow =  row.getInt("users_now");
		this.usersMax =  row.getInt("users_max");
		this.allowPets = row.getBoolean("allow_pets");
		this.allowPetsEat = row.getBoolean("allow_pets_eat");
		this.allowWalkthrough = row.getBoolean("allow_walkthrough");
		this.hideWall = row.getBoolean("hidewall");
		this.wallThickness = row.getInt("wall_thickness");
		this.floorThickness = row.getInt("floor_thickness");
		this.tagFormat = row.getString("tags");*/
		
		return null;
	}
	
}
