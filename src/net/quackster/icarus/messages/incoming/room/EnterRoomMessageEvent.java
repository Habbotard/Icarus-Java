package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomState;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.incoming.room.error.GenericErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.GenericDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.GenericNoAnswerDoorbellMessageComposer;
import net.quackster.icarus.messages.outgoing.room.InitialRoomInfoMessageComposer;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomEnterErrorMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.HotelScreenMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class EnterRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = Icarus.getDao().getRoom().getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		RoomUser roomUser = session.getRoomUser();

		String pass = request.readString();

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
					session.send(new GenericDoorbellMessageComposer(1));
					room.send(new GenericDoorbellMessageComposer(session.getDetails().getUsername()), true);
				} else {

					session.send(new GenericNoAnswerDoorbellMessageComposer());
					session.send(new HotelScreenMessageComposer());
				}

				return;
			}

			if (room.getState() == RoomState.PASSWORD.getState()) {
				if (!pass.equals(room.getPassword())) {
					session.send(new GenericErrorMessageComposer(-100002));
					session.send(new HotelScreenMessageComposer());
					return;
				}
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

		response.appendInt32(room.getScore());
		response.appendBoolean(false);
		session.send(response);

		response = new Response(Outgoing.RoomRightsLevelMessageComposer);
		response.appendInt32(4);
		session.send(response);

		session.send(new PrepareRoomMessageComposer(room));

	}

}
