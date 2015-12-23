package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.outgoing.user.CreditsBalanceMessageComposer;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

public class GetCurrencyBalanceMessageEvent implements MessageEvent {

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
		
		session.send(new CreditsBalanceMessageComposer(session.getDetails().getCredits()));		
		session.getConnection().setSentCurrency(true);
	}

}
