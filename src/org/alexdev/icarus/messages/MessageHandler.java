package org.alexdev.icarus.messages;

import java.util.HashMap;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.incoming.catalogue.CatalogueMessageEvent;
import org.alexdev.icarus.messages.incoming.catalogue.CataloguePageMessageEvent;
import org.alexdev.icarus.messages.incoming.handshake.*;
import org.alexdev.icarus.messages.incoming.messenger.*;
import org.alexdev.icarus.messages.incoming.misc.*;
import org.alexdev.icarus.messages.incoming.navigator.*;
import org.alexdev.icarus.messages.incoming.room.*;
import org.alexdev.icarus.messages.incoming.room.user.*;
import org.alexdev.icarus.messages.incoming.user.*;
import org.alexdev.icarus.netty.readers.*;

public class MessageHandler {

	private HashMap<Short, MessageEvent> messages;

	public MessageHandler() {
		this.messages = new HashMap<Short, MessageEvent>();
		this.register();
	}
	
	public void register() {
		
		this.messages.clear();
		this.registerHandshakePackets();
		this.registerUserPackets();
		this.registerMiscPackets();
		this.registerMessenger();
		this.registerNavigatorPackets();
		this.registerRoomPackets();
		this.registerCataloguePackets();
	}

	private void registerHandshakePackets() {
		this.messages.put(Incoming.VersionCheckMessageEvent, new VersionCheckMessageEvent());
		this.messages.put(Incoming.UniqueIDMessageEvent, new UniqueIDMessageEvent());
		this.messages.put(Incoming.SSOTicketMessageEvent, new SSOTicketMessageEvent());
	}
	
	private void registerUserPackets() {
		this.messages.put(Incoming.InfoRetrieveMessageEvent, new InfoRetrieveMessageEvent());
		this.messages.put(Incoming.GetCurrencyBalanceMessageEvent, new GetCurrencyBalanceMessageEvent());
	}
	
	private void registerMessenger() {
		this.messages.put(Incoming.MessengerFriendsMessageEvent, new MessengerMessageEvent());
		this.messages.put(Incoming.MessengerSearchMessageEvent, new MessengerSearchMessageEvent());
		this.messages.put(Incoming.MessengerRequestMessageEvent, new MessengerRequestMessageEvent());
		this.messages.put(Incoming.MessengerAcceptMessageEvent, new MessengerAcceptMessageEvent());
		this.messages.put(Incoming.MessengerDeclineMessageEvent, new MessengerDeclineMessageEvent());
		this.messages.put(Incoming.MessengerDeleteFriendMessageEvent, new MessengerDeleteFriendMessageEvent());
		this.messages.put(Incoming.MessengerTalkMessageEvent, new MessengerTalkMessageEvent());
		this.messages.put(Incoming.FriendListUpdateMessageEvent, new MessengerUpdateMessageEvent());
		this.messages.put(Incoming.FollowFriendMessageEvent, new FollowFriendMessageEvent());
	}
	
	private void registerNavigatorPackets() {
		this.messages.put(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
		this.messages.put(Incoming.SearchNewNavigatorEvent, new SearchNewNavigatorEvent());
		this.messages.put(Incoming.CreateRoomMessageEvent, new CreateRoomMessageEvent());
		this.messages.put(Incoming.CanCreateRoomMessageEvent, new CanCreateRoomMessageEvent());
	}

	private void registerMiscPackets() {
		this.messages.put(Incoming.EventLogMessageEvent, new EventLogMessageEvent());
		this.messages.put(Incoming.LatencyTestMessageEvent, new LatencyTestMessageEvent());
	}
	
	private void registerRoomPackets() {
		this.messages.put(Incoming.RoomInfoMessageEvent, new RoomInfoMessageEvent());
		this.messages.put(Incoming.GetRoomRightsListMessageEvent, new GetRoomRightsListMessageEvent());
		this.messages.put(Incoming.RequestHeightmapMessageEvent, new EnterRoomMessageEvent());
		this.messages.put(Incoming.RoomSucessMessageEvent, new HeightmapMessageEvent());
		
		// inside room 
		this.messages.put(Incoming.UserWalkMessageEvent, new UserWalkMessageEvent());
		this.messages.put(Incoming.LeaveRoomMessageEvent, new LeaveRoomMessageEvent());
		this.messages.put(Incoming.ChatMessageEvent, new ChatMessageEvent());
		this.messages.put(Incoming.ShoutMessageEvent, new ShoutMessageEvent());
		this.messages.put(Incoming.DanceMessageEvent, new DanceMessageEvent());
		this.messages.put(Incoming.StartTypingMessageEvent, new TypingStatusMessageEvent());
		this.messages.put(Incoming.StopTypingMessageEvent, new TypingStatusMessageEvent());
		this.messages.put(Incoming.RoomThumbnailMessageEvent, new RoomThumbnailMessageEvent());
		
		// doorbell
		this.messages.put(Incoming.DoorbellAnswerMessageEvent, new DoorbellAnswerMessageEvent());
		this.messages.put(Incoming.DoorbellEnterMessageEvent, new DoorbellEnterMessageEvent());
		
		// edit room details
		this.messages.put(Incoming.RoomEditInfoMessageEvent, new RoomSettingsDataMessageEvent());
		this.messages.put(Incoming.SaveRoomMessageEvent, new SaveRoomMessageEvent());
		this.messages.put(Incoming.DeleteRoomMessageEvent, new DeleteRoomMessageEvent());
	}
	
	private void registerCataloguePackets() {
		this.messages.put(Incoming.CatalogueMessageEvent, new CatalogueMessageEvent());
		this.messages.put(Incoming.CataloguePageMessageEvent, new CataloguePageMessageEvent());
	}

	public void handleRequest(Session session, Request message) {
		if (messages.containsKey(message.getMessageId())) {
			messages.get(message.getMessageId()).handle(session, message);
		}
	}

	public HashMap<Short, MessageEvent> getMessages() {
		return messages;
	}

}
