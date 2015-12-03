package net.quackster.icarus.messages.incoming.user;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class LandingLoadWidgetMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		// TODO Auto-generated method stub

		//
		
		String text = request.readString();

		Response response = new Response(Outgoing.LandingWidgetMessageComposer);

		if (text.isEmpty()) {
			response.appendString("");
			response.appendString("");
		}
		session.send(response);
	}

}
