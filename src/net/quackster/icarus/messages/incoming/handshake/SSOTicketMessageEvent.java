package net.quackster.icarus.messages.incoming.handshake;

import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import net.quackster.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import net.quackster.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class SSOTicketMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		session.send(new UniqueMachineIDMessageComposer(session.getMachineId()));
		session.send(new AuthenticationOKMessageComposer());
		session.send(new HomeRoomMessageComposer(2, false));
		session.send(new LandingWidgetMessageComposer());
		
		session.getDetails().setAuthenticated(true); // logged in 
		
		List<Room> myRooms = RoomDao.getPlayerRooms(session.getDetails().getId(), true);
		Log.println("Loaded rooms: " + myRooms.size() + " - total loaded: " + Icarus.getGame().getRoomManager().getLoadedRooms().size());
		
		//RoomDao.getRoom(1, true);
	}
}
