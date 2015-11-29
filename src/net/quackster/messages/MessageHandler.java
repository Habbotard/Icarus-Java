package net.quackster.messages;

import java.util.HashMap;

import net.quackster.icarus.game.user.Session;
import net.quackster.messages.headers.Incoming;
import net.quackster.messages.incoming.handshake.GenerateSecretKeyMessageEvent;
import net.quackster.messages.incoming.handshake.InitCryptoMessageEvent;
import net.quackster.netty.readers.Request;

public class MessageHandler {

	private HashMap<Integer, Message> messages;

	public MessageHandler() {
		this.messages = new HashMap<Integer, Message>();
		this.register();
	}
	
	public void register() {
		
		this.messages.clear();
		this.messages.put(Incoming.InitCryptoMessageEvent, new InitCryptoMessageEvent());
		this.messages.put(Incoming.GenerateSecretKeyMessageEvent, new GenerateSecretKeyMessageEvent());
	}
	
	public void handleRequest(Session session, Request message) {
		
		if (messages.containsKey(message.getMessageId())) {
			messages.get(message.getMessageId()).handle(session, message);
		}
		
	}

}
