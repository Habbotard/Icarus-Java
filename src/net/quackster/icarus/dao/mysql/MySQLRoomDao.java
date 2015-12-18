package net.quackster.icarus.dao.mysql;

import java.sql.PreparedStatement;
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
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.room.settings.RoomType;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLRoomDao implements IRoomDao, IProcessStorage<Room, ResultSet> {

	private MySQLDao dao;
	private Map<String, RoomModel> roomModels;

	public MySQLRoomDao(MySQLDao dao) {

		this.dao = dao;
		this.roomModels = new HashMap<String, RoomModel>();

		try {

			if (dao.isConnected()) {
				ResultSet row = dao.getStorage().getTable("SELECT * FROM room_models");

				while (row.next()) {
					roomModels.put(row.getString("id"), new RoomModel(row.getString("id"), row.getString("heightmap"), row.getInt("door_x"), row.getInt("door_y"), row.getInt("door_z"), row.getInt("door_dir")));
				}

				Storage.releaseObject(row);

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
	public List<Room> getPublicRooms(boolean storeInMemory) {

		List<Room> rooms = new ArrayList<Room>();
		ResultSet row = null;

		try {

			row =  dao.getStorage().getTable("SELECT * FROM rooms WHERE room_type = " + RoomType.PUBLIC.getTypeCode());

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
	public List<Integer> getRoomRights(int roomId) {

		List<Integer> rooms = new ArrayList<Integer>();

		ResultSet row = null;

		try {

			row =  dao.getStorage().getTable("SELECT * FROM room_rights WHERE room_id = " + roomId);

			while (row.next()) {
				rooms.add(row.getInt("user_id"));
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		return rooms;
	}

	@Override
	public Room createRoom(Session session, String name, String description, String model, int category, int usersMax, int tradeState) {
		
		ResultSet row = null;
		
		try {
			
			PreparedStatement statement = dao.getStorage().prepare("INSERT INTO rooms (name, description, owner_id, model, category, users_max, trade_state) VALUES (?, ?, ?, ?, ?, ?, ?)", true); {
				statement.setString(1, name);
				statement.setString(2, description);
				statement.setInt(3, session.getDetails().getId());
				statement.setString(4, model);
				statement.setInt(5, category);
				statement.setInt(6, usersMax);
				statement.setInt(7, tradeState);
				statement.executeUpdate();
			}
			
			row = statement.getGeneratedKeys();
			
			if (row != null && row.next()) {
				return this.getRoom(row.getInt(1), true);
			}
			
		} catch (SQLException e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		
		return null;
	}

	@Override
	public RoomModel getModel(String model) {
		return roomModels.get(model);
	}

	@Override
	public Room fill(Room instance, ResultSet row) throws SQLException {

		RoomType type = RoomType.getType(row.getInt("room_type"));

		CharacterDetails details = null;

		if (type == RoomType.PRIVATE) {
			details = Icarus.getDao().getPlayer().getDetails(row.getInt("owner_id"));
		}

		instance.getData().fill(row.getInt("id"), type, details == null ? 0 : details.getId(), details == null ? "" : details.getUsername(), row.getString("name"), row.getInt("state"), row.getInt("users_now"),
				row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
				row.getInt("category"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
				row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
				row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"), row.getInt("chat_type"), row.getInt("chat_balloon"), row.getInt("chat_speed"),
				row.getInt("chat_max_distance"), row.getInt("chat_flood_protection"), row.getInt("who_can_mute"), row.getInt("who_can_kick"), row.getInt("who_can_ban"));
		
		
		//, int chatType, int chatBalloon, int chatSpeed,
		//int chatMaxDistance, int chatFloodProtection, int whoCanMute, int whoCanKick, int whoCanBan) {

		if (this.dao.getStorage().exists("SELECT * from room_thumbnails WHERE room_id = " + instance.getData().getId())) {
			instance.getData().setThumbnail(this.dao.getStorage().getString("SELECT image_url from room_thumbnails WHERE room_id = " + instance.getData().getId()));

		}

		return null;
	}



}
