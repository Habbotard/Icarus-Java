package net.quackster.icarus.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class RoomDao {

	public static List<Room> getPlayerRooms(int userId) {

		List<Room> rooms = new ArrayList<Room>();

		ResultSet row = null;

		try {

			row =  Icarus.getStorage().getTable("SELECT * FROM rooms WHERE owner_id = " + userId);

			while (row.next()) {
				
				Room room = new Room(row);
				rooms.add(room);
				Icarus.getGame().getRoomManager().add(room);
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}

		return rooms;
	}
	
}
