package net.quackster.icarus.game.messenger;

import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.messenger.FriendUpdateMessageComposer;
import net.quackster.icarus.netty.readers.Response;

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
	
	public void sendStatus(boolean forceOffline) {
		
		Response message = new FriendUpdateMessageComposer(new MessengerFriend(this.session.getDetails().getId()), forceOffline);
		
		for (MessengerFriend friend : this.friends) {
			
			if (friend.isOnline()) {
				System.out.println(this.session.getDetails().getUsername() + " sending (" + forceOffline + ") to " + friend.getDetails().getUsername());
				friend.getSession().send(message);
			}
		}
	}
	
	/*public void statusChange(boolean forceOffline) {
		
		for (MessengerFriend friend : this.friends) {
			
			if (friend.isOnline()) {
				System.out.println("SEND UPDATE " + forceOffline + " TO: " + friend.getDetails().getUsername());
				friend.getSession().send(new FriendUpdateMessageComposer(new MessengerFriend(this.session.getDetails().getId()), forceOffline));
			}
			
			this.session.send(new FriendUpdateMessageComposer(friend, forceOffline));
		}
	}*/
	
	public void dispose() {

		this.sendStatus(true);
		
		if (friends != null) {
			this.friends.clear();
			this.friends = null;
		}

		this.session = null;
	}
	
	public List<MessengerFriend> getFriends() {
		return friends;
	}

	public boolean isInitalised() {
		return initalised;
	}

}
