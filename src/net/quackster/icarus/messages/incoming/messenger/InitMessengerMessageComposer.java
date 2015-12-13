package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.messenger.Messenger;
import net.quackster.icarus.game.messenger.MessengerFriend;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.util.GameSettings;

public class InitMessengerMessageComposer implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		if (session.getMessenger() == null) {
			return;
		}
		
		System.out.println("HELLO MESSENGER#@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		Messenger messenger = session.getMessenger().loadFriends();
		
		Response response = new Response(Outgoing.MessengerCategoriesMessageComposer);
		
		response.appendInt32(GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
		response.appendInt32(GameSettings.MAX_FRIENDS_DEFAULT);
		response.appendInt32(GameSettings.MAX_FRIENDS_BASIC);
		response.appendInt32(GameSettings.MAX_FRIENDS_VIP);
		response.appendInt32(0);
		session.send(response);
		
		response = new Response(Outgoing.InitMessengerMessageComposer);
		response.appendInt32(1);
		response.appendInt32(0);
		response.appendInt32(messenger.getFriends().size());
		
		for (MessengerFriend friend : messenger.getFriends()) {
			
           response.appendInt32(friend.getDetails().getId());
           response.appendString(friend.getDetails().getUsername());
           response.appendInt32(0); // gender
           response.appendBoolean(friend.isOnline());
           response.appendBoolean(friend.inRoom());
           response.appendString(friend.isOnline() ? friend.getDetails().getFigure() : "");
           response.appendInt32(0);
           response.appendString(friend.isOnline() ? friend.getDetails().getMotto() : "");
           response.appendString(""); //realname
           response.appendString("3/2/2015"); // useless
           response.appendBoolean(true); // allow offline message
           response.appendBoolean(false); // persistedMessageUser
           response.appendBoolean(false); // pocketuser
           response.appendShort(0); // relationship_status
		}
		
		session.send(response);
		/*response.appendInt32(messenger.getFriends().size());
		
		for (MessengerFriend friend : messenger.getFriends()) {
			
           response.appendInt32(friend.getDetails().getId());
           response.appendString(friend.getDetails().getUsername());
           response.appendInt32(0); // gender
           response.appendBoolean(friend.isOnline());
           response.appendBoolean(friend.inRoom());
           response.appendString(friend.isOnline() ? friend.getDetails().getFigure() : "");
           response.appendInt32(0);
           response.appendString(friend.isOnline() ? friend.getDetails().getMotto() : "");
           response.appendString(""); //realname
           response.appendString("3/2/2015"); // useless
           response.appendBoolean(true); // allow offline message
           response.appendBoolean(false); // persistedMessageUser
           response.appendBoolean(false); // pocketuser
           response.appendShort(0); // relationship_status
		}*/
		
		//session.send(response);
	}

}

/*        internal static int GetMaxFriends(GameClient Client)
{
if (Client.GetHabbo().Rank >= 4)
    return EmulatorSettings.MAX_FRIENDS_STAFF;

if (Client.GetHabboDataContainer().GetSubscriptionManager().HasSubscription("habbo_club"))
    return EmulatorSettings.MAX_FRIENDS_BASIC;

//if (Client.GetHabboDataContainer().GetSubscriptionManager().HasSubscription("habbo_club"))
//    return EmulatorSettings.MAX_FRIENDS_VIP;

return EmulatorSettings.MAX_FRIENDS_DEFAULT;
}*/