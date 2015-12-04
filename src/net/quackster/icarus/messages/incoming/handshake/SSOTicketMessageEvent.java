package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import net.quackster.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class SSOTicketMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		session.send(new UniqueMachineIDMessageComposer(session.getMachineId()));
		session.send(new AuthenticationOKMessageComposer());
		
		Response response = new Response(Outgoing.HomeRoomMessageComposer);
        response.appendInt32(2); // Home Room ID
        response.appendInt32(false); // force enter room on connect
        session.send(response);
	}

}
