package net.quackster.icarus.game.room.bot;

import net.quackster.icarus.game.entity.EntityType;
import net.quackster.icarus.game.entity.IEntity;
import net.quackster.icarus.game.entity.IRoomEntity;
import net.quackster.icarus.game.user.CharacterDetails;

public class Bot implements IEntity {

	private CharacterDetails details;
	private BotRoomUser roomUser;

	public Bot() {
		this.roomUser = new BotRoomUser(this);
		this.details = new CharacterDetails();
		this.details.fill(2200, "Lolz", "hello theur", "fa-1201-0.lg-270-91.hd-180-1.sh-300-91.ch-805-84.hr-125-42");
	}
	
	@Override
	public IRoomEntity getRoomUser() {
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
