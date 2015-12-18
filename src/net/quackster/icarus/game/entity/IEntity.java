package net.quackster.icarus.game.entity;

import net.quackster.icarus.game.user.CharacterDetails;

public interface IEntity {

	public CharacterDetails getDetails();
	public IRoomEntity getRoomUser();
	public EntityType getType();
}
