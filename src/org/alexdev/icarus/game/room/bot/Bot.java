package org.alexdev.icarus.game.room.bot;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.entity.AbstractRoomEntity;
import org.alexdev.icarus.game.user.CharacterDetails;

public class Bot implements IEntity {

	private CharacterDetails details;
	private BotRoomUser roomUser;

	public Bot() {
		this.roomUser = new BotRoomUser(this);
		this.details = new CharacterDetails(this);
		this.details.fill(2200, "Lolz", "hello theur", "fa-1201-0.lg-270-91.hd-180-1.sh-300-91.ch-805-84.hr-125-42", 1, 0);
	}
	
	@Override
	public AbstractRoomEntity getRoomUser() {
		return this.roomUser;
	}

	@Override
	public EntityType getType() {
		return EntityType.BOT;
	}

	@Override
	public CharacterDetails getDetails() {
		return details;
	}

}
