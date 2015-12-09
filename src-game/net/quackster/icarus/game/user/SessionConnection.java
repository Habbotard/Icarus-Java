package net.quackster.icarus.game.user;

import net.quackster.icarus.messages.headers.Incoming;

public class SessionConnection {

	private Session session;
	private boolean sentCurrency;
	private boolean sentNewNavigator;


	public SessionConnection(Session session) {
		this.session = session;
		this.sentCurrency = false;
		this.sentNewNavigator = false;
	}
	
	// for slower connections, sometimes packets can be skipped when logging in.
	// this is usually caused when the client is taking ages to load and finally connects to the server
	public void packetCheck() {

		if (!session.getConnection().hasSentCurrency()) {
			session.invoke(Incoming.GetCurrencyBalanceMessageEvent, null);
		}

		if (!session.getConnection().hasSentNewNavigator()) {
			session.invoke(Incoming.NewNavigatorMessageEvent, null);
		}
	}

	public void dispose() {
		this.session = null;
	}

	public boolean hasSentCurrency() {
		return  sentCurrency;
	}

	public void setSentCurrency(boolean sentCurrencyPacket) {
		this.sentCurrency = sentCurrencyPacket;
	}

	public boolean hasSentNewNavigator() {
		return sentNewNavigator;
	}

	public void setSentNewNavigator(boolean sentNewNavigator) {
		this.sentNewNavigator = sentNewNavigator;
	}


}
