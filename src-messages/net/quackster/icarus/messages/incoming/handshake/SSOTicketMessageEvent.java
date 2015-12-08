package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.dao.room.RoomDao;
import net.quackster.icarus.dao.user.PlayerDao;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import net.quackster.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import net.quackster.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class SSOTicketMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		boolean loginSucess = PlayerDao.login(session, request.readString());
		
		if (!loginSucess) {
			session.close();
		}
		
		session.checkForDuplicates();
		
		session.send(new UniqueMachineIDMessageComposer(session.getMachineId()));
		session.send(new AuthenticationOKMessageComposer());
		session.send(new HomeRoomMessageComposer(2, false));
		session.send(new LandingWidgetMessageComposer());
		
		RoomDao.getPlayerRooms(session.getDetails(), true);
	}
}
