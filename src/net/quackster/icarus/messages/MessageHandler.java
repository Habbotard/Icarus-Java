package net.quackster.icarus.messages;

import java.util.HashMap;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.incoming.handshake.GenerateSecretKeyMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.InitCryptoMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.SSOTicketMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.UniqueIDMessageEvent;
import net.quackster.icarus.messages.incoming.handshake.VersionCheckMessageEvent;
import net.quackster.icarus.messages.incoming.misc.EventLogMessageEvent;
import net.quackster.icarus.messages.incoming.misc.RequestLatencyTestMessageEvent;
import net.quackster.icarus.messages.incoming.navigator.NewNavigatorMessageEvent;
import net.quackster.icarus.messages.incoming.navigator.SearchNewNavigatorEvent;
import net.quackster.icarus.messages.incoming.room.EnterPrivateRoomMessageEvent;
import net.quackster.icarus.messages.incoming.room.RoomGetHeightmapMessageEvent;
import net.quackster.icarus.messages.incoming.room.RoomSuccessMessageEvent;
import net.quackster.icarus.messages.incoming.user.GetCurrencyBalanceMessageEvent;
import net.quackster.icarus.messages.incoming.user.InfoRetrieveMessageEvent;
import net.quackster.icarus.netty.readers.Request;

public class MessageHandler {

	private HashMap<Short, Message> messages;

	public MessageHandler() {
		this.messages = new HashMap<Short, Message>();
		this.register();
	}
	
	public void register() {
		
		this.messages.clear();
		this.registerHandshakePackets();
		this.registerUserPackets();
		this.registerMiscPackets();
		this.registerNavigatorPackets();
		this.registerRoomPackets();
	
	}
	
	private void registerRoomPackets() {
		this.messages.put(Incoming.EnterPrivateRoomMessageEvent, new EnterPrivateRoomMessageEvent());
		this.messages.put(Incoming.RoomGetHeightmapMessageEvent, new RoomGetHeightmapMessageEvent());
		this.messages.put(Incoming.RoomSuccessMessageEvent, new RoomSuccessMessageEvent());
	}

	private void registerHandshakePackets() {
		this.messages.put(Incoming.InitCryptoMessageEvent, new InitCryptoMessageEvent());
		this.messages.put(Incoming.VersionCheckMessageEvent, new VersionCheckMessageEvent());
		this.messages.put(Incoming.GenerateSecretKeyMessageEvent, new GenerateSecretKeyMessageEvent());
		this.messages.put(Incoming.UniqueIDMessageEvent, new UniqueIDMessageEvent());
		this.messages.put(Incoming.SSOTicketMessageEvent, new SSOTicketMessageEvent());
	}
	
	private void registerUserPackets() {
		this.messages.put(Incoming.InfoRetrieveMessageEvent, new InfoRetrieveMessageEvent());
		this.messages.put(Incoming.GetCurrencyBalanceMessageEvent, new GetCurrencyBalanceMessageEvent());
	}
	
	private void registerNavigatorPackets() {
		this.messages.put(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
		this.messages.put(Incoming.SearchNewNavigatorEvent, new SearchNewNavigatorEvent());
	}

	private void registerMiscPackets() {
		this.messages.put(Incoming.EventLogMessageEvent, new EventLogMessageEvent());
		this.messages.put(Incoming.RequestLatencyTestMessageEvent, new RequestLatencyTestMessageEvent());
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
