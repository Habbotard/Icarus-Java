package net.quackster.icarus.game.room.player;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.entity.IRoomEntity;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.outgoing.room.notify.FloodFilterMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.TalkMessageComposer;
import net.quackster.icarus.util.GameSettings;

public class RoomUser extends IRoomEntity {

	private boolean inRoom;
	private boolean isLoadingRoom;

	private long chatFloodTimer;
	private int chatCount;
	
	private Session session;
	//private RoomModel model;

	public RoomUser(Session session) {

		this.session = session;
		this.setEntity(session);
		
		this.reset();
	}

	public void reset() {
		
		super.dispose();
		
		this.chatFloodTimer = 0;
		this.chatCount = 0;
		
		this.isLoadingRoom = false;
		this.inRoom = false;
	}

	public void chat(String message, int bubble, int count, boolean shout, boolean spamCheck) {

		// if current time less than the chat flood timer (last chat time + seconds to check)
		// say that they still need to wait before shouting again
		if (spamCheck) {
			if (Icarus.getUtilities().getTimestamp() < this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD && !session.getDetails().hasFuse("moderator")) {
				session.send(new FloodFilterMessageComposer(GameSettings.CHAT_FLOOD_WAIT));
				return;
			}
		}

		// TODO: Check if not bot
		// The below function validates the chat bubbles
		if (bubble == 2 || (bubble == 23 && !session.getDetails().hasFuse("moderator")) || bubble < 0 || bubble > 29) {
			bubble = super.getLastChatId();
		}

		super.getRoom().send(new TalkMessageComposer(this, shout, message, count, bubble));

		// if the users timestamp has passed the check but the chat count is still high
		// the chat count is reset then

		if (spamCheck) {
			if (!session.getDetails().hasFuse("moderator")) {

				if (Icarus.getUtilities().getTimestamp() > this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD) {
					this.chatCount = 0;
				} else {
					this.chatCount = this.chatCount + 1;
				}

				this.setChatFloodTimer(Icarus.getUtilities().getTimestamp() + GameSettings.CHAT_FLOOD_SECONDS);

			}
		}
	}


	public void dispose() {

		this.session = null;
		super.dispose();

	}

	public boolean inRoom() {
		return inRoom && !this.isLoadingRoom; // player is actually inside the room and not busy loading it
	}

	public void setInRoom(boolean inRoom) {
		this.inRoom = inRoom;
	}

	public boolean isLoadingRoom() {
		return isLoadingRoom;
	}

	public void setLoadingRoom(boolean isLoadingRoom) {
		this.isLoadingRoom = isLoadingRoom;
	}

	public void setChatFloodTimer(long lastChatTime) {
		this.chatFloodTimer = lastChatTime;
	}

	public int getChatCount() {
		return chatCount;
	}

	public void setChatCount(int chatCount) {
		this.chatCount = chatCount;
	}
}
