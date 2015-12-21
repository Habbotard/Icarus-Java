package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class EnterRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		String pass = request.readString();
		
		if (room.getEntities().contains(session)) {
			return;
		}
		
		boolean isOwner = room.hasRights(session.getDetails().getId(), true);
		session.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));

		if (room.getData().getUsersNow() >= room.getData().getUsersMax()) {

			if (!session.getDetails().hasFuse("user_enter_full_rooms") && session.getDetails().getId() != room.getData().getOwnerId()) {

				session.send(new RoomEnterErrorMessageComposer(1));
				session.send(new HotelViewMessageComposer());
				return;
			}
		}

		if (room.getData().getState().getStateCode() > 0 && !room.hasRights(session.getDetails().getId(), false)) {
			if (room.getData().getState() == RoomState.DOORBELL) {

				if (room.getUsers().size() > 0) {
					session.send(new HotelViewMessageComposer());
					session.send(new GenericDoorbellMessageComposer(1));
					room.send(new GenericDoorbellMessageComposer(session.getDetails().getUsername()), true);
				} else {

					session.send(new GenericNoAnswerDoorbellMessageComposer());
					session.send(new HotelViewMessageComposer());
				}

				return;
			}

			if (room.getData().getState() == RoomState.PASSWORD) {
				if (!pass.equals(room.getData().getPassword())) {
					session.send(new GenericErrorMessageComposer(-100002));
					session.send(new HotelViewMessageComposer());
					return;
				}
			}
		}
		
		room.loadRoom(session);
	}

}
