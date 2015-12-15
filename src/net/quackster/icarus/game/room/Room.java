package net.quackster.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.model.Point;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.room.player.RoomSearch;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomModelMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
import net.quackster.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import net.quackster.icarus.netty.readers.Response;

public class Room {
	
	private int privateId;
	private int[][] collisionMap;
	private boolean disposed;

	private RoomSearch search;
	private RoomData data;
	
	private List<Session> users;
	
	private ScheduledFuture<?> tickTask;

	public Room() {
		this.search = new RoomSearch(this);
		this.data = new RoomData(this);
		this.users = new ArrayList<Session>();
	}
	
	public void leaveRoom(Session session, boolean hotelView) {

		if (hotelView) {;
			session.send(new HotelViewMessageComposer());
		}

		this.send(new RemoveUserMessageComposer(session.getRoomUser().getVirtualId()));

		RoomUser roomUser = session.getRoomUser();

		roomUser.stopWalking(false);
		roomUser.reset();

		this.users.remove(session);

		if (this.users.size() == 0) {

			if (this.tickTask != null) {
				this.tickTask.cancel(true);
				this.tickTask = null;
			}
			
			this.dispose();
		}
		
		session.getMessenger().sendStatus(false, false);
	}

	public boolean hasRights(int userId) {

		if (this.data.getOwnerId() == userId) {
			return true;
		} else {
			return this.data.getRights().contains(userId);
		}
	}
	
	public void loadRoom(Session session) {

		RoomUser roomUser = session.getRoomUser();
		
		roomUser.setRoom(this);
		roomUser.setLoadingRoom(true);
		roomUser.getStatuses().clear();

		session.send(new RoomModelMessageComposer(this));

		if (!this.data.getFloor().equals("0")) {
			session.send(new RoomSpacesMessageComposer("floor", this.data.getFloor()));
		}

		if (!this.data.getWall().equals("0")) {
			session.send(new RoomSpacesMessageComposer("wallpaper", this.data.getWall()));
		}

		session.send(new RoomSpacesMessageComposer("landscape", this.data.getLandscape()));
		session.send(new RoomRatingMessageComposer(roomUser, this.data.getScore()));
		session.send(new RoomRightsLevelMessageComposer(roomUser));
		session.send(new PrepareRoomMessageComposer(this));

	}


	public void regenerateCollisionMap() {

		if (this.data.getModel() == null) {
			return;
		}

		int mapSizeX = this.data.getModel().getMapSizeX();
		int mapSizeY = this.data.getModel().getMapSizeY();

		int[][] collisionMap = new int[mapSizeX][mapSizeY]; 

		for(int y = 0; y < mapSizeY; y++) {
			for(int x = 0; x < mapSizeX; x++) {

				if (this.data.getModel().getSquareStates()[x][y] == RoomModel.OPEN) {

					collisionMap[x][y] = RoomModel.OPEN;

					if (!this.getData().isAllowWalkthrough()) { // if the room doesn't want players to be able to walk into each other

						if (this.search.findUser(new Point(x, y)) != null) {
							collisionMap[x][y] = RoomModel.CLOSED;
						}
					}

				} else {
					collisionMap[x][y] = RoomModel.CLOSED;
				}
			}
		}

		this.collisionMap = collisionMap;
	}


	public void send(Response response, boolean checkRights) {

		if (this.disposed) {
			return;
		}

		for (Session session : this.users) {
			if (checkRights && this.hasRights(session.getDetails().getId())) {
				Log.println("(" + session.getDetails().getUsername() + ") SENT: " + response.getHeader() + " / " + response.getBodyString());
				session.send(response);
			}
		}
	}


	public void send(Response response) {

		if (this.disposed) {
			return;
		}

		for (Session session : this.users) {
			session.send(response);
		}
	}

	public RoomData getData() {
		return data;
	}
	
	public RoomSearch getSearch() {
		return this.search;
	}

	public List<Session> getUsers() {
		return users;
	}
	
	public void setUsers(ArrayList<Session> arrayList) {
		this.users = arrayList;
	}
	
	public int getVirtualId() {
		this.privateId = this.privateId + 1;
		return this.privateId;
	}

	public int[][] getCollisionMap() {
		return collisionMap;
	}
	
	public void setTickTask(ScheduledFuture<?> task) {
		this.tickTask = task;
	}

	public String getPassword() {
		return "xd";
	}
	
	public void dispose() {

		if (this.disposed) {
			return;
		}

		try {

			if (this.users.size() > 0 || (Icarus.getServer().getSessionManager().findById(this.data.getOwnerId()) != null)) {
				return;
			}

			Icarus.getGame().getRoomManager().getLoadedRooms().remove(this);

			this.collisionMap = null;
			this.tickTask = null;
			
			this.users.clear();
			this.users = null;
			
			this.data.dispose();
			this.data = null;
			
			this.search.dispose();
			this.search = null;

			this.disposed = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
