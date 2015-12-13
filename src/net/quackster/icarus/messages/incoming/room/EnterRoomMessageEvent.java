package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomState;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import net.quackster.icarus.netty.readers.Request;

public class EnterRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		String pass = request.readString();

		if (room.getUsersNow() >= room.getUsersMax()) {

			if (!session.getDetails().hasFuse("user_enter_full_rooms") && session.getDetails().getId() != room.getOwnerId()) {

				session.send(new RoomEnterErrorMessageComposer(1));
				session.send(new HotelViewMessageComposer());
				return;
			}
		}

		if (room.getState().getStateCode() > 0 && !room.hasRights(session.getDetails().getId())) {
			if (room.getState() == RoomState.DOORBELL) {

				if (room.getUsers().size() > 0) {
					session.send(new GenericDoorbellMessageComposer(1));
					room.send(new GenericDoorbellMessageComposer(session.getDetails().getUsername()), true);
				} else {

					session.send(new GenericNoAnswerDoorbellMessageComposer());
					session.send(new HotelViewMessageComposer());
				}

				return;
			}

			if (room.getState() == RoomState.PASSWORD) {
				if (!pass.equals(room.getPassword())) {
					session.send(new GenericErrorMessageComposer(-100002));
					session.send(new HotelViewMessageComposer());
					return;
				}
			}
		}
		
		room.loadRoom(session);
	}

}
