package net.quackster.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import net.quackster.icarus.game.entity.EntityType;
import net.quackster.icarus.game.entity.IEntity;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class UserDisplayMessageComposer extends Response {

	public UserDisplayMessageComposer(IEntity entity) {
		this(Arrays.asList(new IEntity[] { entity }));
	}

	public UserDisplayMessageComposer(List<IEntity> entities) {

		this.init(Outgoing.UserDisplayMessageComposer);

		synchronized (entities) {

			this.appendInt32(entities.size());

			for (IEntity entity : entities) {

				if (entity.getType() == EntityType.PLAYER) {


					this.appendInt32(entity.getDetails().getId());
					this.appendString(entity.getDetails().getUsername());
					this.appendString(entity.getDetails().getMotto());
					this.appendString(entity.getDetails().getFigure());
					this.appendInt32(entity.getRoomUser().getVirtualId());
					this.appendInt32(entity.getRoomUser().getX());
					this.appendInt32(entity.getRoomUser().getY());
					this.appendString(Double.toString(entity.getRoomUser().getHeight()));
					this.appendInt32(0);
					this.appendInt32(1);
					this.appendString("m");
					this.appendInt32(-1);
					this.appendInt32(-1);
					this.appendInt32(0);
					this.appendInt32(1337); // achievement points
					this.appendBoolean(false);

				}

				if (entity.getType() == EntityType.BOT) {


					this.appendInt32(entity.getDetails().getId());
					this.appendString(entity.getDetails().getUsername());
					this.appendString(entity.getDetails().getMotto());
					this.appendString(entity.getDetails().getFigure());
					this.appendInt32(entity.getRoomUser().getVirtualId());
					this.appendInt32(entity.getRoomUser().getX());
					this.appendInt32(entity.getRoomUser().getY());
					this.appendString(Double.toString(entity.getRoomUser().getHeight()));
					this.appendInt32(0);
					this.appendInt32(4); // 2 if pet
					
					// TODO: pet shit here
					
					this.appendString("m");
		            this.appendInt32(1);
		            this.appendString("Alex");
		            this.appendInt32(5);
		            this.appendShort(1);
		            this.appendShort(2);
		            this.appendShort(3);
		            this.appendShort(4);
		            this.appendShort(5);

				}
			}
		}
	}

}
