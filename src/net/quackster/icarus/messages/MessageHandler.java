package net.quackster.icarus.messages;

import java.util.HashMap;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.headers.Incoming;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.incoming.handshake.*;
import net.quackster.icarus.messages.incoming.messenger.InitMessengerMessageComposer;
import net.quackster.icarus.messages.incoming.misc.*;
import net.quackster.icarus.messages.incoming.navigator.*;
import net.quackster.icarus.messages.incoming.room.*;
import net.quackster.icarus.messages.incoming.room.user.ChatMessageEvent;
import net.quackster.icarus.messages.incoming.room.user.DanceMessageEvent;
import net.quackster.icarus.messages.incoming.room.user.ShoutMessageEvent;
import net.quackster.icarus.messages.incoming.room.user.TypingStatusMessageEvent;
import net.quackster.icarus.messages.incoming.room.user.UserWalkMessageEvent;
import net.quackster.icarus.messages.incoming.user.*;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

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
		this.registerMessenger();
		this.registerNavigatorPackets();
		this.registerRoomPackets();
	}

	private void registerMessenger() {
		this.messages.put(Incoming.InitMessengerMessageComposer, new InitMessengerMessageComposer());
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
	
	private void registerRoomPackets() {
		this.messages.put(Incoming.RoomInfoMessageEvent, new RoomInfoMessageEvent());
		this.messages.put(Incoming.RequestHeightmapMessageEvent, new EnterRoomMessageEvent());
		this.messages.put(Incoming.RoomSucessMessageEvent, new HeightmapMessageEvent());
		this.messages.put(Incoming.UserWalkMessageEvent, new UserWalkMessageEvent());
		this.messages.put(Incoming.LeaveRoomMessageEvent, new LeaveRoomMessageEvent());
		this.messages.put(Incoming.ChatMessageEvent, new ChatMessageEvent());
		this.messages.put(Incoming.ShoutMessageEvent, new ShoutMessageEvent());
		this.messages.put(Incoming.DanceMessageEvent, new DanceMessageEvent());
		this.messages.put(Incoming.StartTypingMessageEvent, new TypingStatusMessageEvent());
		this.messages.put(Incoming.StopTypingMessageEvent, new TypingStatusMessageEvent());
		
		this.messages.put(Incoming.DoorbellAnswerMessageEvent, new DoorbellAnswerMessageEvent());
		this.messages.put(Incoming.DoorbellEnterMessageEvent, new DoorbellEnterMessageEvent());
	}

	public void handleRequest(Session session, Request message) {
		
		if (message.getMessageId() == 3895) {
			Response response = new Response(Outgoing.RelationshipMessageComposer);
			response.appendInt32(message.readInt());
			response.appendInt32(0);
			//session.send(response);
			return;
		}
		
		if (message.getMessageId() == 213) {
			Response response = new Response(Outgoing.SendRoomCampaignFurnitureMessageComposer);
			response.appendInt32(0);
			session.send(response);
		}
		
		
		/*            Response.Init(LibraryParser.OutgoingRequest("RelationshipMessageComposer"));
            Response.AppendInteger(habboForId.Id);
            Response.AppendInteger(habboForId.Relationships.Count);
            foreach (var current in habboForId.Relationships.Values)
            {
                var habboForId2 = AzureEmulator.GetHabboById(Convert.ToUInt32(current.UserId));
                if (habboForId2 == null)
                {
                    Response.AppendInteger(0);
                    Response.AppendInteger(0);
                    Response.AppendInteger(0);
                    Response.AppendString("Placeholder");
                    Response.AppendString("hr-115-42.hd-190-1.ch-215-62.lg-285-91.sh-290-62");
                }
                else
                {
                    Response.AppendInteger(current.Type);
                    Response.AppendInteger((current.Type == 1) ? num : ((current.Type == 2) ? num2 : num3));
                    Response.AppendInteger(current.UserId);
                    Response.AppendString(habboForId2.UserName);
                    Response.AppendString(habboForId2.Look);
                }
            }*/
		
		
		
		if (messages.containsKey(message.getMessageId())) {
			messages.get(message.getMessageId()).handle(session, message);
		}
	}

	public HashMap<Short, Message> getMessages() {
		return messages;
	}

}
