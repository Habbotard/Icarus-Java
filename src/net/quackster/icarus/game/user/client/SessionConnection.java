package net.quackster.icarus.game.user.client;

public class SessionConnection {

	private boolean sentCurrency;
	private boolean sentNewNavigator;

	public SessionConnection() {
		this.sentCurrency = false;
		this.sentNewNavigator = false;
	}
	
	public void dispose() {

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
