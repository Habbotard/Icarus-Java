package net.quackster.icarus.game.messenger;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.netty.readers.Response;

public class MessengerUser {

	private int userId;
	private CharacterDetails details;
	private Session session;

	public MessengerUser(int userId) {
		this.userId = userId;

		if (this.isOnline()) {
			this.details = this.session.getDetails();
		} else {
			this.details = Icarus.getDao().getPlayer().getDetails(this.userId);
		}


	}

	public void update() {
		this.session = Icarus.getServer().getSessionManager().findById(this.userId);
	}

	public void serialise(Response response, boolean forceOffline) {

		response.appendInt32(this.getDetails().getId());
		response.appendString(this.getDetails().getUsername());
		response.appendInt32(0); // gender
		response.appendBoolean(forceOffline ? false : this.isOnline());
		response.appendBoolean(forceOffline ? false : this.inRoom());

		if (forceOffline) {
			response.appendString("");
			response.appendInt32(0);
			response.appendString("");  
		} else {
			response.appendString(this.isOnline() ? this.getDetails().getFigure() : "");
			response.appendInt32(0);
			response.appendString(this.isOnline() ? this.getDetails().getMotto() : "");  
		}

		response.appendString(""); //realname
		response.appendString(""); // useless
		response.appendBoolean(true); // allow offline message
		response.appendBoolean(false); // persistedMessageUser
		response.appendBoolean(false); // pocketuser
		response.appendShort(0); 
	}

	public void searchSerialise(Response response) {

		response.appendInt32(this.getDetails().getId());
		response.appendString(this.getDetails().getUsername());
		response.appendString(this.getDetails().getMotto()); // gender
		response.appendBoolean(this.isOnline());
		response.appendBoolean(this.inRoom());
		response.appendString("");
		response.appendInt32(0);
		response.appendString(this.isOnline() ? this.getDetails().getFigure() : ""); 
		response.appendString(""); //realname
	}



	public void dispose() {
		this.session = null;
		this.details = null;
	}

	public Session getSession() {
		return session;
	}

	public CharacterDetails getDetails() {
		return details;
	}

	public int getUserId() {
		return userId;
	}

	public boolean isOnline() {
		this.update();
		return session != null;
	}

	public boolean inRoom() {
		return isOnline() ? session.getRoomUser().inRoom() : false;
	}

}
