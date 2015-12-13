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
import net.quackster.icarus.messages.outgoing.room.user.HotelScreenMessageComposer;
import net.quackster.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import net.quackster.icarus.netty.readers.ISerialize;
import net.quackster.icarus.netty.readers.Response;

public class Room implements ISerialize {

	private int id;
	private int ownerId;
	private String ownerName;
	private String name;
	private RoomState state;
	private int usersNow;
	private int usersMax;
	private String description;
	private int tradeState;
	private int score;
	private int category;
	private int groupId;
	private String model;
	private String wall;
	private String floor;
	private String landscape;
	private boolean allowPets;
	private boolean allowPetsEat;
	private boolean allowWalkthrough;
	private boolean hideWall;
	private int wallThickness;
	private int floorThickness;

	private int[][] collisionMap;

	private String tagFormat;

	private List<Session> users;
	private List<Integer> rights;

	private ScheduledFuture<?> tickTask;

	private int privateId;
	private boolean disposed;

	private RoomSearch search;

	public Room() {
		this.privateId = 0;
		this.users = new ArrayList<Session>();
		this.search = new RoomSearch(this);
	}

	public void fill(int id, int ownerId, String ownerName, String name, int state, int usersNow, int usersMax,
			String description, int tradeState, int score, int category, int groupId, String model, String wall,
			String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
			boolean hideWall, int wallThickness, int floorThickness, String tagFormat) {

		this.disposed = false;
		this.id = id;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.name = name;
		this.state = RoomState.getState(state);
		this.usersNow = usersNow;
		this.usersMax = usersMax;
		this.description = description;
		this.tradeState = tradeState;
		this.score = score;
		this.category = category;
		this.groupId = groupId;
		this.model = model;
		this.wall = wall;
		this.floor = floor;
		this.landscape = landscape;
		this.allowPets = allowPets;
		this.allowPetsEat = allowPetsEat;
		this.allowWalkthrough = allowWalkthrough;
		this.hideWall = hideWall;
		this.wallThickness = wallThickness;
		this.floorThickness = floorThickness;
		this.tagFormat = tagFormat;
		this.rights = Icarus.getDao().getRoom().getRoomRights(this.id);
	}
	
	public void leaveRoom(Session session, boolean hotelView) {

		if (hotelView) {;
		session.send(new HotelScreenMessageComposer());
		}

		this.send(new RemoveUserMessageComposer(session.getRoomUser().getVirtualId()));

		RoomUser roomUser = session.getRoomUser();

		roomUser.stopWalking(false);
		roomUser.reset();

		this.getUsers().remove(session);

		if (this.users.size() == 0) {

			if (this.tickTask != null) {
				this.tickTask.cancel(true);
				this.tickTask = null;
			}

			this.rights.clear();
			this.rights = null;

			this.dispose();
		}
	}

	public boolean hasRights(int userId) {

		if (this.ownerId == userId) {
			return true;
		} else {
			return this.rights.contains(userId);
		}
	}

	public void dispose() {

		if (this.disposed) {
			return;
		}

		try {

			if (this.users.size() > 0 && (Icarus.getServer().getSessionManager().findById(this.ownerId) != null)) {
				return;
			}

			Icarus.getGame().getRoomManager().getLoadedRooms().remove(this);

			this.name = null;
			this.ownerName = null;
			this.description = null;
			this.tagFormat = null;
			this.landscape = null;
			this.model = null;
			this.wall = null;
			this.collisionMap = null;
			this.tickTask = null;

			this.users.clear();
			this.users = null;

			this.disposed = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void serialise(Response response) {
		response.appendInt32(id);
		response.appendString(this.name);
		response.appendInt32(this.ownerId);
		response.appendString(this.ownerName);
		response.appendInt32(this.state.getStateCode());
		response.appendInt32(this.usersNow);
		response.appendInt32(this.usersMax);
		response.appendString(this.description);
		response.appendInt32(this.tradeState);
		response.appendInt32(this.score);
		response.appendInt32(0); // Ranking
		response.appendInt32(this.category);
		response.appendInt32(0); //TagCount

		/*int enumType = enterRoom ? 32 : 0;

		if (this.roomType .equals("private")) {
			enumType += 8;
		}

		if (this.allowPets) { 
			enumType += 16;
		}*/

		response.appendInt32(0);

	}


	public void regenerateCollisionMap() {

		if (this.getModel() == null) {
			return;
		}

		int mapSizeX = this.getModel().getMapSizeX();
		int mapSizeY = this.getModel().getMapSizeY();

		int[][] collisionMap = new int[mapSizeX][mapSizeY]; 

		for(int y = 0; y < mapSizeY; y++) {
			for(int x = 0; x < mapSizeX; x++) {

				if (this.getModel().getSquareStates()[x][y] == RoomModel.OPEN) {

					collisionMap[x][y] = RoomModel.OPEN;

					if (!this.allowWalkthrough) { // if the room doesn't want players to be able to walk into each other

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

	public RoomSearch getSearch() {
		return this.search;
	}

	public int getVirtualId() {
		this.privateId = this.privateId + 1;
		return this.privateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomState getState() {
		return state;
	}

	public void setState(int state) {
		this.state = RoomState.getState(state);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTradeState() {
		return tradeState;
	}

	public void setTradeState(int tradeState) {
		this.tradeState = tradeState;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public boolean isAllowPets() {
		return allowPets;
	}

	public void setAllowPets(boolean allowPets) {
		this.allowPets = allowPets;
	}

	public boolean isAllowPetsEat() {
		return allowPetsEat;
	}

	public void setAllowPetsEat(boolean allowPetsEat) {
		this.allowPetsEat = allowPetsEat;
	}

	public boolean isAllowWalkthrough() {
		return allowWalkthrough;
	}

	public void setAllowWalkthrough(boolean allowWalkthrough) {
		this.allowWalkthrough = allowWalkthrough;
	}

	public boolean isHideWall() {
		return hideWall;
	}

	public void setHideWall(boolean hideWall) {
		this.hideWall = hideWall;
	}

	public int getWallThickness() {
		return wallThickness;
	}


	public void setWallThickness(int wallThickness) {
		this.wallThickness = wallThickness;
	}

	public Integer getWallHeight() {
		return -1;
	}

	public int getFloorThickness() {
		return floorThickness;
	}

	public void setFloorThickness(int floorThickness) {
		this.floorThickness = floorThickness;
	}

	public int getId() {
		return id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setUsersMax(int usersMax) {
		this.usersMax = usersMax;
	}

	public int getUsersMax() {
		return this.usersMax;
	}

	public int getUsersNow() {

		if (this.users == null) {
			this.users = new ArrayList<Session>();
		}

		this.usersNow = this.users.size();
		return usersNow;
	}

	public RoomModel getModel() {
		return Icarus.getDao().getRoom().getModel(this.model);
	}

	public String getTagFormat() {
		return tagFormat;
	}

	public List<Session> getUsers() {
		return users;
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

}
