package net.quackster.icarus.messages.incoming.user;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.netty.readers.Request;

public class RequestLatencyTestMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		// for slower connections, sometimes packets can be skipped when logging in.
		// this is usually caused when the client is taking ages to load and finally connects to the server
		
		if (!session.getConnection().hasSentCurrency()) {
			session.invoke(Incoming.GetCurrencyBalanceMessageEvent, null);
		}
		
		if (!session.getConnection().hasSentNewNavigator()) {
			session.invoke(Incoming.NewNavigatorMessageEvent, null);
		}
	}

}
