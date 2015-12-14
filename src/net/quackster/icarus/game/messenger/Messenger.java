package net.quackster.icarus.game.messenger;

import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
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

	public void sendStatus(boolean firstConnection, boolean forceOffline) {

		Response message = new MessengerUpdateMessageComposer(new MessengerFriend(this.session.getDetails().getId()), forceOffline);

		for (MessengerFriend friend : this.friends) {

			if (friend.isOnline()) {

				if (firstConnection) {
				
					boolean alwaysOnline = friend.getSession().getMessenger().getFriends().stream().filter(f -> f.getDetails().getId() == this.session.getDetails().getId()).findAny().get().wasAlwaysOnline();

					if (!alwaysOnline) {
						friend.getSession().send(message);
					}
				} else {
					friend.getSession().send(message);
				}
			}
		}
	}

	public void dispose() {

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
