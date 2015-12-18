package net.quackster.icarus.messages.incoming.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.outgoing.navigator.CreateRoomMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.util.GameSettings;

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
