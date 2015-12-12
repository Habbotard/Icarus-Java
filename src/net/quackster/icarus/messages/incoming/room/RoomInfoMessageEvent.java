package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomState;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.room.DoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.DoorbellNoOneMessageComposer;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomEnterErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.HotelScreenMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class RoomInfoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		RoomUser roomUser = session.getRoomUser();

		boolean forwardPlayer = true;

		if (roomUser.inRoom()) {

			if (roomUser.getRoom() != room) {
				roomUser.getRoom().leaveRoom(session, false);
			} else {
				forwardPlayer = false;
			}
		}


		if (roomUser.isLoadingRoom()) {
			forwardPlayer = false;
		}

		if (forwardPlayer) {

			if (room.getUsersNow() >= room.getUsersMax()) {

				if (!session.getDetails().hasFuse("user_enter_full_rooms") && session.getDetails().getId() != room.getOwnerId()) {

					session.send(new RoomEnterErrorMessageComposer(1));
					session.send(new HotelScreenMessageComposer());
					return;
				}
			}

			if (room.getState() > 0 && !room.hasRights(session.getDetails().getId())) {

				if (room.getState() == RoomState.DOORBELL.getState()) {

					if (room.getUsers().size() == 0) {

						session.send(new DoorbellNoOneMessageComposer());
						session.send(new HotelScreenMessageComposer());
					} else {

						//room.send(new DoorbellMessageComposer(session.getDetails().getUsername()), true);
						//session.send(new DoorbellMessageComposer(""));
						session.send(new DoorbellNoOneMessageComposer());
						session.send(new HotelScreenMessageComposer());
					}

					return;
				}
			}
		}

		session.send(new RoomDataMessageComposer(room, session, forwardPlayer));
	}

}