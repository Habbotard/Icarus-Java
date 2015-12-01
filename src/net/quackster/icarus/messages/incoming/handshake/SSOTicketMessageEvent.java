package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import net.quackster.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class SSOTicketMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		session.send(new UniqueMachineIDMessageComposer(session.getMachineId()));
		session.send(new AuthenticationOKMessageComposer());
		
		Response response = new Response(Outgoing.HomeRoomMessageComposer);
        response.appendInt32(1); // Home Room ID
        response.appendInt32(0); // Home Room ID
        session.send(response);
        
       /* response.init(Outgoing.LoadFriendsMessageComposer);
        response.appendInt32(1);
        response.appendInt32(0);
        response.appendInt32(0);
        session.send(response);*/
        
        /*public class UserClubRightsMessageComposer {
    public static ServerMessage compose() {
        ServerMessage msg = new ServerMessage(EServerMessage.UserClubRightsMessageComposer);
        msg.writeInt(2); // 0 - no club, 2 - club
        msg.writeInt(7); // rank
        msg.writeInt(0); // ?

        return msg;
    }
}*/
        
        /*public class MinimailCountMessageComposer {
    public static ServerMessage compose() {
        ServerMessage msg = new ServerMessage(EServerMessage.MinimailCountMessageComposer);
        msg.writeInt(2); // Minimail unread count
        return msg;
    }
}*/

	}

}
