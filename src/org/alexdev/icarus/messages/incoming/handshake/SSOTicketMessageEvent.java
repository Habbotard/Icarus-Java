package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class SSOTicketMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		boolean loginSuccess = Icarus.getDao().getPlayer().login(session, request.readString());
		
		if (!loginSuccess) {
			session.close();
			return;
		}
		
		if (session.getMachineId() == null) {
			session.close();
			return;
		}
		
		if (Icarus.getServer().getSessionManager().checkForDuplicates(session)) {
			session.close();
			return;
		}
		
		session.send(new UniqueMachineIDMessageComposer(session.getMachineId()));
		session.send(new AuthenticationOKMessageComposer());
		session.send(new HomeRoomMessageComposer(2, false));
		session.send(new LandingWidgetMessageComposer());
		
		Icarus.getDao().getRoom().getPlayerRooms(session.getDetails(), true);
	}
}
