package net.quackster.icarus.messages.incoming.room;

import net.quackster.icarus.dao.room.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomDataMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomUpdateMessageComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class EnterPrivateRoomMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Room room = RoomDao.getRoom(request.readInt(), true);
		
		if (room == null) {
			return;
		}
	
		if (session.getRoomUser().isLoadingRoom()) {
			return;
		}
		
		session.getRoomUser().setLoadingRoom(true);
		
		if (session.getRoomUser().isInRoom()) {

			if (session.getRoomUser().getRoom() != room) {
				session.getRoomUser().getRoom().leaveRoom(session, false);
			}
		}
		
		session.getRoomUser().setRoom(room);
		
		boolean forwardPlayer = false;
        int num = request.readInt();
        int num2 = request.readInt();

        if (num == 0 && num2 == 1) {
        	forwardPlayer = true;
        }
        
        session.send(new RoomDataMessageComposer(room, session, forwardPlayer));
		session.send(new PrepareRoomMessageComposer());
		
		if (!room.getFloor().equals("0")) {
		session.send(new RoomSpacesMessageComposer("floor", room.getFloor()));
		}
		
		if (!room.getWall().equals("0")) {
		session.send(new RoomSpacesMessageComposer("wallpaper", room.getWall()));
		}
		
		session.send(new RoomSpacesMessageComposer("landscape", room.getLandscape()));
		
		/*            if (currentLoadingRoom.CheckRights(Session, true, false))
            {
                Response.Init(LibraryParser.OutgoingRequest("RoomRightsLevelMessageComposer"));
                Response.AppendInteger(4);
                queuedServerMessage.AppendResponse(GetResponse());
                Response.Init(LibraryParser.OutgoingRequest("HasOwnerRightsMessageComposer"));
                queuedServerMessage.AppendResponse(GetResponse());
            }
            else if (currentLoadingRoom.CheckRights(Session, false, true))
            {
                Response.Init(LibraryParser.OutgoingRequest("RoomRightsLevelMessageComposer"));
                Response.AppendInteger(1);
                queuedServerMessage.AppendResponse(GetResponse());
            }
            else
            {
                Response.Init(LibraryParser.OutgoingRequest("RoomRightsLevelMessageComposer"));
                Response.AppendInteger(0);
                queuedServerMessage.AppendResponse(GetResponse());
            }*/
		
		/*            Response.Init(LibraryParser.OutgoingRequest("RoomRatingMessageComposer"));
            Response.AppendInteger(currentLoadingRoom.RoomData.Score);
            Response.AppendBool(!Session.GetHabbo().RatedRooms.Contains(currentLoadingRoom.RoomId) &&
                                !currentLoadingRoom.CheckRights(Session, true, false));
            queuedServerMessage.AppendResponse(GetResponse());

            Response.Init(LibraryParser.OutgoingRequest("RoomUpdateMessageComposer"));
            Response.AppendInteger(currentLoadingRoom.RoomId);
            queuedServerMessage.AppendResponse(GetResponse());*/
		

		Response response = new Response(Outgoing.RoomRatingMessageComposer);
        response.appendInt32(0);
        response.appendBoolean(false); // did i rate it and NOT owner
        session.send(response);
		
		response = new Response(Outgoing.RoomRightsLevelMessageComposer);
        response.appendInt32(0);
        session.send(response);
		
		session.send(new RoomUpdateMessageComposer(room.getId()));
		
		

	}

}
