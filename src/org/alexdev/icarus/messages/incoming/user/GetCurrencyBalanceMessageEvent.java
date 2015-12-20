package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

public class GetCurrencyBalanceMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Response response = new Response();
		response.init(Outgoing.ActivityPointsMessageComposer);
		response.appendInt32(2);
		response.appendInt32(0);
		response.appendInt32(1337);
		response.appendInt32(5);
		response.appendInt32(44444);
		session.send(response);
		
		response.init(Outgoing.CreditsBalanceMessageComposer);
		response.appendString("333.0");
		session.send(response);
		
		session.getConnection().setSentCurrency(true);
	}

}
