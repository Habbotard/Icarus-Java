package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.user.CharacterDetails;

public interface IEntity {

	public CharacterDetails getDetails();
	public AbstractRoomEntity getRoomUser();
	public EntityType getType();
}
