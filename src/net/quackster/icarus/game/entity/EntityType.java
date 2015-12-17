package net.quackster.icarus.game.entity;

import net.quackster.icarus.game.user.Session;

public enum EntityType {

	PLAYER(Session.class),
	PET(IEntity.class),
	BOT(IEntity.class);
	
	Class<? extends IEntity> clazz;
	
	EntityType(Class<? extends IEntity> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends IEntity> getClazz() {
		return clazz;
	}
	
}
