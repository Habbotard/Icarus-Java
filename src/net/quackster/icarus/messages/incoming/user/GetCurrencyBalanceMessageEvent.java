package net.quackster.icarus.messages.incoming.user;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

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
