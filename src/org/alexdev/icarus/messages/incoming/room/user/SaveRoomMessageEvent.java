package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.ChatOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomSettingsOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomSettingsUpdatedMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class SaveRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (!room.hasRights(session, true)) {
			return;
		}
		
		request.readInt(); // room id
		
		RoomData data = room.getData();
		
		String oldName = data.getName();
		
        data.setName(request.readString());
        
        if (data.getName().length() < 3) {
        	data.setName(oldName);
        }

        data.setDescription(request.readString());
        data.setState(request.readInt());
        
        if (data.getState().getStateCode() < 0 || data.getState().getStateCode() > 3) {
            data.setState(0);
        }
        
        data.setPassword(request.readString());
        data.setUsersMax(request.readInt());
        data.setCategory(request.readInt());
        
        int tagCount = request.readInt();

        if (tagCount > 2) {
        	return;
        }
        
        String[] tags = new String[tagCount];

        for (int i = 0; i < tagCount; i++) {
          tags[i] = request.readString();
        }
        
        data.setTags(tags);
        data.setTradeState(request.readInt());
        data.setAllowPets(request.readBoolean());
        data.setAllowPetsEat(request.readBoolean());
        data.setAllowWalkthrough(request.readBoolean());
        data.setHideWall(request.readBoolean());
        
        data.setWallThickness(request.readInt());
        if (data.getWallThickness() < -2 || data.getWallThickness() > 1) {
        	data.setWallThickness(0);
        }

        data.setFloorThickness(request.readInt());
        if (data.getFloorThickness() < -2 || data.getFloorThickness() > 1) {
        	data.setFloorThickness(0);
        }
        
        data.setWhoCanMute(request.readInt());
        data.setWhoCanKick(request.readInt());
        data.setWhoCanBan(request.readInt());
        data.setChatType(request.readInt());
        data.setChatBalloon(request.readInt());
        data.setChatSpeed(request.readInt());
        data.setChatMaxDistance(request.readInt());
        
        if (data.getChatMaxDistance() > 90) {
        	data.setChatMaxDistance(90);
        }
        
        data.setChatFloodProtection(request.readInt());
        
        if (data.getChatFloodProtection() > 2) {
        	data.setChatFloodProtection(1);
        }        
        
        Icarus.getDao().getRoom().updateRoom(room);
        
		session.send(new ChatOptionsMessageComposer(room));
		session.send(new WallOptionsMessageComposer(room));
		
		session.send(new RoomSettingsOKMessageComposer(room));
		session.send(new RoomSettingsUpdatedMessageComposer(room));
	}

}
