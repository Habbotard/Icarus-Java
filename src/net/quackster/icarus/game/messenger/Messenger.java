package net.quackster.icarus.game.messenger;

import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;

public class Messenger {

	private boolean initalised;
	private Session session;
	private List<MessengerFriend> friends;

	public Messenger(Session session) {
		this.session = session;
		this.initalised = false;
	}

	public Messenger loadFriends() {

		this.friends = Icarus.getDao().getMessenger().getFriends(session.getDetails().getId());
		this.initalised = true;
		
		return this;
	}

	public List<MessengerFriend> getFriends() {
		return friends;
	}

	public void dispose() {

		if (friends != null) {
			this.friends.clear();
			this.friends = null;
		}

		this.session = null;
	}

	public boolean isInitalised() {
		return initalised;
	}

}
