package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

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
