package net.quackster.icarus.dao;

import java.util.List;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.CharacterDetails;

public interface IRoomDao {

	public List<Room> getPlayerRooms(CharacterDetails details);
	public List<Room> getPlayerRooms(CharacterDetails details, boolean storeInMemory);
	public Room getRoom(int roomId);
	public Room getRoom(int roomId, boolean storeInMemory);
	public RoomModel getModel(String model);
}
