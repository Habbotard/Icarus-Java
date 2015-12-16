package net.quackster.icarus.messages.incoming.messenger;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class FollowFriendMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
        int friendId = request.readInt();
        int errorID = -1;

        Session client = session.getMessenger().getFriend(friendId).getSession();

        Response response = new Response();
        
        if (client != null)
        {
            if (client.getRoomUser().inRoom())
            {
            	response.init(Outgoing.FollowBuddyMessageComposer);
                response.appendInt32(client.getRoomUser().getRoomId());
            	session.send(response);
            }

            else errorID = 2; // User is not in a room
        }
        else
            errorID = 1; // User is offline

        if (errorID != -1) {
            response.init(Outgoing.FollowErrorMessageComposer);
            response.appendInt32(errorID);
            session.send(response);
        }
	}

}
