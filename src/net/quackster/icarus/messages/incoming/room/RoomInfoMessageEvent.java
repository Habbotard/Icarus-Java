package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomState;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.GenericDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.GenericNoAnswerDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.InitialRoomInfoMessageComposer;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomEnterErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
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

		boolean Loadroom = request.readIntAsBool();
		boolean StalkingRoom = request.readIntAsBool();

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

			session.send(new RoomDataMessageComposer(room, session, Loadroom, StalkingRoom));

			if (room.getUsersNow() >= room.getUsersMax()) {

				if (!session.getDetails().hasFuse("user_enter_full_rooms") && session.getDetails().getId() != room.getOwnerId()) {

					session.send(new RoomEnterErrorMessageComposer(1));
					session.send(new HotelScreenMessageComposer());
					return;
				}
			}

			if (room.getState() > 0 && !room.hasRights(session.getDetails().getId())) {

				if (room.getState() == RoomState.DOORBELL.getState()) {

					if (room.getUsers().size() > 0) {

						//Response response = new Response(164);


						//session.send(response);

						session.send(new GenericDoorbellMessageComposer(1));
						room.send(new GenericDoorbellMessageComposer(session.getDetails().getUsername()), true);

					} else {

						session.send(new GenericNoAnswerDoorbellMessageComposer());
						session.send(new HotelScreenMessageComposer());

					}

					return;
				}
			}


			roomUser.setRoom(room);
			roomUser.setLoadingRoom(true);
			roomUser.getStatuses().clear();

			session.send(new InitialRoomInfoMessageComposer(room));

			if (!room.getFloor().equals("0")) {
				session.send(new RoomSpacesMessageComposer("floor", room.getFloor()));
			}

			if (!room.getWall().equals("0")) {
				session.send(new RoomSpacesMessageComposer("wallpaper", room.getWall()));
			}

			session.send(new RoomSpacesMessageComposer("landscape", room.getLandscape()));


			Response response = new Response(Outgoing.RoomRatingMessageComposer);

			if (roomUser.getRoom().getOwnerId() == session.getDetails().getId()) {
				response.appendInt32(4);
				roomUser.getStatuses().put("flatctrl 1", "");
			} else {
				response.appendInt32(0);
			}
			response.appendBoolean(false); // did i rate it and NOT owner
			session.send(response);

			response = new Response(Outgoing.RoomRightsLevelMessageComposer);
			response.appendInt32(4);
			session.send(response);

			session.send(new PrepareRoomMessageComposer(room));
		}

		//session.send(new RoomDataMessageComposer(room, session, forwardPlayer));
	}

}
