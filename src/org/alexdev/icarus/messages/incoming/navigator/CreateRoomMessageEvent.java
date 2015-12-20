package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.outgoing.navigator.CreateRoomMessageComposer;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.util.GameSettings;

public class CreateRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String name = request.readString();
        String description = request.readString();
        String model = request.readString();
        int category = request.readInt();
        int usersMax = request.readInt();
        int tradeState = request.readInt();
        
        if (session.getRooms().size() >= GameSettings.MAX_ROOMS_PER_ACCOUNT) {
        	session.invoke(Incoming.CanCreateRoomMessageEvent, null);
        	return;
        }
        
        if (name == null || description == null || model == null) {
        	return;
        }
        
        Room room = Icarus.getDao().getRoom().createRoom(session, name, description, model, category, usersMax, tradeState);
        
        session.send(new CreateRoomMessageComposer(room.getData().getId(), room.getData().getName()));

	}

}
