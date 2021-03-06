package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.ChatOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class HeightmapMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		Room room = session.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (room.getEntities().contains(session)) {
			return;
		}

		RoomModel roomModel = room.getData().getModel();
		
		session.send(new HeightMapMessageComposer(roomModel));
		session.send(new FloorMapMessageComposer(room));
		
		session.getRoomUser().setLoadingRoom(false);
		session.getRoomUser().setInRoom(true);
		
		room.firstEntry(); // this method will load all pets if this is the first user to join the room
		
		RoomUser user = session.getRoomUser();

		user.setVirtualId(room.getVirtualId());
		user.setX(roomModel.getDoorX());
		user.setY(roomModel.getDoorY());
		user.setRotation(roomModel.getDoorRot(), false);
		user.setHeight(roomModel.getSquareHeight()[user.getX()][user.getY()]);

		room.send(new UserDisplayMessageComposer(session));
		room.send(new UserStatusMessageComposer(session));

		if (!room.getEntities().contains(session)) {
			room.getEntities().add(session);
		}

		session.send(new UserDisplayMessageComposer(room.getEntities()));
		session.send(new UserStatusMessageComposer(room.getEntities()));

		for (Session players : room.getUsers()) {
			if (players.getRoomUser().isDancing()) {
				session.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
			}
		}

		if (room.hasRights(session.getDetails().getId(), false)) {
			session.getRoomUser().getStatuses().put("flatctrl", "1");
		}		
		
		session.send(new RoomDataMessageComposer(room, session, true, true));
		
		session.send(new ChatOptionsMessageComposer(room));
		session.send(new WallOptionsMessageComposer(room));
		
		session.getMessenger().sendStatus(false);
		
	}
}