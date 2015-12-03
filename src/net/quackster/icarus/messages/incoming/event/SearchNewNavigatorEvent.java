package net.quackster.icarus.messages.incoming.event;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.habbohotel.navigator.Navigator;
import net.quackster.icarus.habbohotel.room.Room;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class SearchNewNavigatorEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String tab = request.readString();
		String ticket = request.readString();
		
		System.out.println(tab);
		
		Response response = new Response();
		
		response.init(Outgoing.SearchResultSetComposer);
        response.appendString(tab);
        response.appendString(ticket);
        response.appendInt32(ticket.length() > 0 ? 1 : Navigator.getNewNavigatorLength(tab));
        
        if (ticket.length() >  0) {

        	response.appendString("");
        	response.appendString("search query");
        	response.appendInt32(2);
        	response.appendBoolean(false);
        	response.appendInt32(0);
        	response.appendInt32(0);
        	
        	new Room().serialiseNavigatorListing(response, false);
        	
        } else {
        	
        	Navigator.serializeNavigatorList(tab, response, session);
        }
        
        session.send(response);
	}

}
