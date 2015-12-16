package net.quackster.icarus.messages.incoming.room;

import java.util.concurrent.TimeUnit;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomCycle;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.FloorMapMessageComposer;
import net.quackster.icarus.messages.outgoing.room.HeightMapMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.DanceMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class HeightmapMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}

		RoomModel roomModel = room.getData().getModel();

		session.send(new HeightMapMessageComposer(roomModel));
		session.send(new FloorMapMessageComposer(room));
		
		session.getRoomUser().setLoadingRoom(false);
		session.getRoomUser().setInRoom(true);
		
		if (room.getUsers().size() == 0) {
			room.setTickTask(Icarus.getUtilities().getThreadPooling().getScheduledThreadPool().scheduleAtFixedRate(new RoomCycle(room), 0, 500, TimeUnit.MILLISECONDS));
			room.regenerateCollisionMap();
		}

		RoomUser user = session.getRoomUser();

		user.setVirtualId(room.getVirtualId());
		user.setX(roomModel.getDoorX());
		user.setY(roomModel.getDoorY());
		user.setRotation(roomModel.getDoorRot(), false);
		user.setHeight(roomModel.getSquareHeight()[user.getX()][user.getY()]);

		room.send(new UserDisplayMessageComposer(session));
		room.send(new UserStatusMessageComposer(session));

		if (!room.getUsers().contains(session)) {
			room.getUsers().add(session);
		}

		session.send(new UserDisplayMessageComposer(room.getUsers()));
		session.send(new UserStatusMessageComposer(room.getUsers()));

		for (Session players : room.getUsers()) {
			if (players.getRoomUser().isDancing()) {
				session.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
			}
		}

		session.send(new RoomDataMessageComposer(room, session, true, true));
		session.getMessenger().sendStatus(false);
	}
}