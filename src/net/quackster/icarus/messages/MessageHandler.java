package net.quackster.icarus.messages;

import java.util.HashMap;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.incoming.handshake.GenerateSecretKeyMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.InitCryptoMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.SSOTicketMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.UniqueIDMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.VersionCheckMessageEvent;
import net.quackster.icarus.messages.incoming.user.LandingLoadWidgetMessageEvent;
import net.quackster.icarus.messages.incoming.user.NewNavigatorMessageEvent;
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
		
		this.messages.put(Incoming.LandingLoadWidgetMessageEvent, new LandingLoadWidgetMessageEvent());
		this.messages.put(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
	}
	
	public void handleRequest(Session session, Request message) {
		
		if (messages.containsKey(message.getMessageId())) {
			messages.get(message.getMessageId()).handle(session, message);
		}
		
	}

}
