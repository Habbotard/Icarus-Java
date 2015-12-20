package org.alexdev.icarus.dao;

import java.util.List;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.user.CharacterDetails;
import org.alexdev.icarus.game.user.Session;

public interface IRoomDao {

	public List<Room> getPublicRooms(boolean storeInMemory);
	public List<Room> getPlayerRooms(CharacterDetails details);
	public List<Room> getPlayerRooms(CharacterDetails details, boolean storeInMemory);
	public Room getRoom(int roomId);
	public Room getRoom(int roomId, boolean storeInMemory);
	public List<Integer> getRoomRights(int roomId);
	public void updateRoom(Room room);
	public RoomModel getModel(String model);
	public Room createRoom(Session session, String name, String description, String model, int category, int usersMax, int tradeState);
	public void deleteRoom(Room room);
}
