package net.quackster.icarus.messages;

import java.util.HashMap;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.incoming.handshake.GenerateSecretKeyMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.InitCryptoMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.SSOTicketMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.UniqueIDMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.VersionCheckMessageEvent;
import net.quackster.icarus.messages.incoming.navigator.NewNavigatorMessageEvent;
import net.quackster.icarus.messages.incoming.navigator.SearchNewNavigatorEvent;
import net.quackster.icarus.messages.incoming.user.GetCurrencyBalanceMessageEvent;
import net.quackster.icarus.messages.incoming.user.InfoRetrieveMessageEvent;
import net.quackster.icarus.messages.incoming.user.LandingLoadWidgetMessageEvent;
import net.quackster.icarus.messages.incoming.user.RequestLatencyTestMessageEvent;
import net.quackster.icarus.netty.readers.Request;

public class MessageHandler {

	private HashMap<Short, Message> messages;

	public MessageHandler() {
		this.messages = new HashMap<Short, Message>();
		this.register();
	}
	
	public void register() {
		
		this.messages.clear();
		this.messages.put(Incoming.InitCryptoMessageEvent, new InitCryptoMessageEvent());
		this.messages.put(Incoming.VersionCheckMessageEvent, new VersionCheckMessageEvent());
		this.messages.put(Incoming.GenerateSecretKeyMessageEvent, new GenerateSecretKeyMessageEvent());
		this.messages.put(Incoming.UniqueIDMessageEvent, new UniqueIDMessageEvent());
		this.messages.put(Incoming.SSOTicketMessageEvent, new SSOTicketMessageEvent());
		
		this.messages.put(Incoming.RequestLatencyTestMessageEvent, new RequestLatencyTestMessageEvent());
		
		this.messages.put(Incoming.LandingLoadWidgetMessageEvent, new LandingLoadWidgetMessageEvent());
		this.messages.put(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
		this.messages.put(Incoming.InfoRetrieveMessageEvent, new InfoRetrieveMessageEvent());
		this.messages.put(Incoming.GetCurrencyBalanceMessageEvent, new GetCurrencyBalanceMessageEvent());
		
		// Navigatur
		this.messages.put(Incoming.SearchNewNavigatorEvent, new SearchNewNavigatorEvent());
	}
	
	public void handleRequest(Session session, Request message) {
		
		if (messages.containsKey(message.getMessageId())) {
			messages.get(message.getMessageId()).handle(session, message);
		}
		
	}

	public HashMap<Short, Message> getMessages() {
		return messages;
	}

}
