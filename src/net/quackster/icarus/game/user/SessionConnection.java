package net.quackster.icarus.game.user;

import net.quackster.icarus.messages.headers.Incoming;

public class SessionConnection {

	private Session session;
	private boolean sentCurrency;
	private boolean sentNewNavigator;
	private boolean sentMessengerStatus;


	public SessionConnection(Session session) {
		this.session = session;
		this.sentCurrency = false;
		this.sentNewNavigator = false;
		this.sentMessengerStatus = false;
	}
	
	// for slower connections, sometimes packets can be skipped when logging in.
	// this is usually caused when the client is taking ages to load and finally connects to the server
	public void packetCheck(short id) {

		if (!session.getConnection().hasSentCurrency()) {
			session.invoke(Incoming.GetCurrencyBalanceMessageEvent, null);
		}

		if (!session.getConnection().hasSentNewNavigator()) {
			session.invoke(Incoming.NewNavigatorMessageEvent, null);
		}
		
		if (id == Incoming.LatencyTestMessageEvent) {
			// send messenger update on delay because otherwise if we send it instantly after friends list
			// the player will appear duped in the console if two people log in at the same time
			// "LatencyTestMessageEvent" sends when all the intro packets are finished
			if (!session.getConnection().hasSentMessengerStatus()) {
				
				if (session.getMessenger().hasInitalised()) {
					session.getMessenger().sendStatus(false);
				}
			}
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

	public boolean hasSentMessengerStatus() {
		return sentMessengerStatus;
	}

	public void setSentMessengerStatus(boolean sentMessengerStatus) {
		this.sentMessengerStatus = sentMessengerStatus;
	}


}
