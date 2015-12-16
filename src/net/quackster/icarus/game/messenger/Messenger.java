package net.quackster.icarus.game.messenger;

import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import net.quackster.icarus.netty.readers.Response;

public class Messenger {

	private boolean initalised;
	private Session session;

	private List<MessengerUser> friends;
	private List<MessengerUser> requests;

	public Messenger(Session session) {
		this.session = session;
		this.initalised = false;
	}

	public void load() {
		this.friends = Icarus.getDao().getMessenger().getFriends(session.getDetails().getId());
		this.requests = Icarus.getDao().getMessenger().getRequests(session.getDetails().getId());
	}


	public boolean hasReqest(int id) {
		return this.getRequest(id) != null;
	}
	
	public boolean isFriend(int id) {
		return this.getFriend(id) != null;
	}
	
	public MessengerUser getFriend(int id) {
		try {
			return this.friends.stream().filter(f -> f.getDetails().getId() == id).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public MessengerUser getRequest(int id) {
		try {
			return this.requests.stream().filter(r -> r.getDetails().getId() == id).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}


	public void removeFriend(int id) {
		MessengerUser user = this.getFriend(id);
		this.friends.remove(user);
	}
	
	public void sendStatus(boolean forceOffline) {

		Response message = new MessengerUpdateMessageComposer(new MessengerUser(this.session.getDetails().getId()), forceOffline);

		for (MessengerUser friend : this.friends) {

			if (friend.isOnline()) {
				if (friend.getSession().getMessenger().hasInitalised()) {
					friend.getSession().send(message);
				}
			}
		}
	}
	
	public void dispose() {

		if (this.friends != null) {
			this.friends.clear();
			this.friends = null;
		}

		if (this.requests != null) {
			this.requests.clear();
			this.requests = null;
		}

		this.session = null;
	}

	public List<MessengerUser> getFriends() {
		return friends;
	}

	public List<MessengerUser> getRequests() {
		return requests;
	}

	public boolean hasInitalised() {
		return initalised;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}
}
