package net.quackster.icarus.game.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.netty.readers.ISerialize;
import net.quackster.icarus.netty.readers.Response;

public class MessengerFriend implements ISerialize {

	private int userId;
	private CharacterDetails details;
	private Session session;
	
	public MessengerFriend(int userId) {
		this.userId = userId;
		this.session = Icarus.getServer().getSessionManager().findById(userId);
		this.details = Icarus.getDao().getPlayer().getDetails(this.userId);
	}
	
	@Override
	public void serialise(Response response) {
		
      response.appendInt32(this.getDetails().getId());
      response.appendString(this.getDetails().getUsername());
      response.appendInt32(0); // gender
      response.appendBoolean(this.isOnline());
      response.appendBoolean(this.inRoom());
      response.appendString(this.isOnline() ? this.getDetails().getFigure() : "");
      response.appendInt32(0);
      response.appendString(this.isOnline() ? this.getDetails().getMotto() : "");
      response.appendString(""); //realname
      response.appendString("3/2/2015"); // useless
      response.appendBoolean(true); // allow offline message
      response.appendBoolean(false); // persistedMessageUser
      response.appendBoolean(false); // pocketuser
      response.appendShort(0); 
	}
	
	
	public CharacterDetails getDetails() {
		return details;
	}

	public int getUserId() {
		return userId;
	}

	public boolean isOnline() {
		return session != null;
	}

	public boolean inRoom() {
		return isOnline() ? session.getRoomUser().inRoom() : false;
	}



}
