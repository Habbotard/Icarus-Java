package net.quackster.icarus.messages.outgoing.room;

import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomData;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.util.GameSettings;

public class RoomSettingsDataMessageComposer extends Response {

	public RoomSettingsDataMessageComposer(Room room) {
		
		RoomData data = room.getData();
		
		this.init(Outgoing.RoomEditSettingsComposer);
        this.appendInt32(data.getId());
        this.appendString(data.getName());
        this.appendString(data.getDescription());
        this.appendInt32(data.getState().getStateCode());
        this.appendInt32(data.getCategory());
        this.appendInt32(data.getUsersMax());
        this.appendInt32(data.getUsersMax());
        this.appendInt32(0);
        /*foreach (var s in room.RoomData.Tags)
        {
            this.appendString(s);
        }*/
        this.appendInt32(data.getTradeState());
        this.appendInt32(data.isAllowPets());
        this.appendInt32(data.isAllowPetsEat());
        this.appendInt32(data.isAllowWalkthrough());
        this.appendInt32(data.isHideWall());
        this.appendInt32(data.getWallThickness());
        this.appendInt32(data.getFloorThickness());
        this.appendInt32(data.getChatType());//room.RoomData.ChatType);
        this.appendInt32(data.getChatBalloon());//room.RoomData.ChatBalloon);
        this.appendInt32(data.getChatSpeed());//room.RoomData.ChatSpeed);
        this.appendInt32(data.getChatMaxDistance());//room.RoomData.ChatMaxDistance);
        this.appendInt32(data.getChatFloodProtection());//room.RoomData.ChatFloodProtection > 2 ? 2 : room.RoomData.ChatFloodProtection);
        this.appendBoolean(false); //allow_dyncats_checkbox
        this.appendInt32(data.getWhoCanMute());//room.RoomData.WhoCanMute);
        this.appendInt32(data.getWhoCanKick());//room.RoomData.WhoCanKick);
        this.appendInt32(data.getWhoCanBan());//room.RoomData.WhoCanBan);
	}

}
