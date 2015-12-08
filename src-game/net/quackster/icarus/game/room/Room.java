package net.quackster.icarus.game.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.room.RoomDao;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.room.RoomUsersMessageComposer;
import net.quackster.icarus.messages.outgoing.room.UpdateUserStatusMessageComposer;
import net.quackster.icarus.messages.outgoing.room.UserLeftRoomMessageComposer;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.game.pathfinder.Point;

public class Room {

	private int id;
	private int ownerId;
	private String ownerName;
	private String name;
	private int state;
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
	private String tagFormat;

	private ConcurrentLinkedQueue<Session> users;
	private ScheduledFuture<?> tickTask;

	public Room(ResultSet row, String ownerName) throws SQLException {

		this.users = new ConcurrentLinkedQueue<Session>();
		
		this.id = row.getInt("id");
		this.ownerId = row.getInt("owner_id");
		this.ownerName = ownerName;
		this.groupId = row.getInt("group_id");
		this.name = row.getString("name");
		this.description = row.getString("description");
		this.state = row.getInt("state");
		this.tradeState = row.getInt("trade_state");
		this.model = row.getString("model");
		this.wall = row.getString("wallpaper");
		this.floor = row.getString("floor");
		this.landscape = row.getString("outside");
		this.usersNow =  row.getInt("users_now");
		this.usersMax =  row.getInt("users_max");
		this.allowPets = row.getBoolean("allow_pets");
		this.allowPetsEat = row.getBoolean("allow_pets_eat");
		this.allowWalkthrough = row.getBoolean("allow_walkthrough");
		this.hideWall = row.getBoolean("hidewall");
		this.wallThickness = row.getInt("wall_thickness");
		this.floorThickness = row.getInt("floor_thickness");
		this.tagFormat = row.getString("tags");
	}

	public int[][] regenerateCollisionMap() {
		
		int mapSizeX = this.getModel().getMapSizeX();
		int mapSizeY = this.getModel().getMapSizeY();
		
		int[][] collisionMap = new int[mapSizeX][mapSizeY]; 

		for(int y = 0; y < mapSizeY; y++) {
			for(int x = 0; x < mapSizeX; x++) {
				
				if (this.getModel().getSquareStates()[x][y] == RoomModel.OPEN) {

					collisionMap[x][y] = RoomModel.OPEN;
					
					if (!this.allowWalkthrough) { // if the room doesn't want players to be able to walk into each other
						if (RoomLocator.findUser(this, new Point(x, y)) != null) {
							collisionMap[x][y] = RoomModel.CLOSED;
						}
					}

				} else {
					collisionMap[x][y] = RoomModel.CLOSED;
				}
			}
		}

		return collisionMap;
	}
	

	public void finaliseRoomEnter(Session session) {

		if (this.users.size() == 0) {
			Log.println("[ROOM " + this.id + "] Pathfinder task start");
			this.tickTask = Icarus.getUtilities().getThreadPooling().getScheduledThreadPool().scheduleAtFixedRate(new RoomCycle(this), 0, 500, TimeUnit.MILLISECONDS);
		}

		if (!this.users.contains(session)) {
			this.users.add(session);
		}

		SessionRoom user = session.getRoomUser();

		user.setX(this.getModel().getDoorX());
		user.setY(this.getModel().getDoorY());
		user.setRotation(this.getModel().getDoorRot());
		user.setHeight(this.getModel().getSquareHeight()[user.getX()][user.getY()]);

		this.send(new RoomUsersMessageComposer(this.users));
		this.send(new UpdateUserStatusMessageComposer(this.users));

	}

	public void leaveRoom(Session session, boolean hotelView) {

		this.send(new UserLeftRoomMessageComposer(session.getDetails().getId()));
		
		SessionRoom roomUser = session.getRoomUser();
		
		roomUser.getStatuses().clear();
		roomUser.stopWalking(false);
		
		this.getUsers().remove(session);

		if (this.users.size() == 0) {
			if (this.tickTask != null) {

				Log.println("[ROOM " + this.id + "] Pathfinder task finish");

				this.tickTask.cancel(true);
				this.tickTask = null;
			}
		}

		if (hotelView) {

			Response response = new Response(Outgoing.OutOfRoomMessageComposer);
			response.appendInt32(3);
			session.send(response);
		}
	}

	public void serialise(Response response, boolean showEvents, boolean enterRoom) {
		response.appendInt32(id);
		response.appendString(this.name);
		response.appendInt32(this.ownerId);
		response.appendString(this.ownerName);
		response.appendInt32(this.state);
		response.appendInt32(this.usersNow);
		response.appendInt32(this.usersMax);
		response.appendString(this.description);
		response.appendInt32(this.tradeState);
		response.appendInt32(this.score);
		response.appendInt32(0); // Ranking
		response.appendInt32(this.category);
		response.appendInt32(0); //TagCount

		int enumType = enterRoom ? 32 : 0;

		String roomType = "private";
		if (roomType.equals("private")) enumType += 8;
		if (this.allowPets) enumType += 16;

		response.appendInt32(enumType);

	}


	public void send(Response response) {
		for (Session session : this.users) {
			session.send(response);
		}
	}

	public void dispose() {

		if (this.users.size() > 0) {
			return;
		}

		this.name = null;
		this.ownerName = null;
		this.description = null;
		this.tagFormat = null;
		this.landscape = null;
		this.model = null;
		this.wall = null;

		Icarus.getGame().getRoomManager().getLoadedRooms().remove(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public int getUsersNow() {

		if (this.users == null) {
			this.users = new ConcurrentLinkedQueue<Session>();
		}

		this.usersNow = this.users.size();
		return usersNow;
	}

	public RoomModel getModel() {
		return RoomDao.getModel(this.model);
	}

	public String getTagFormat() {
		return tagFormat;
	}

	public void setUsersMax(int usersMax) {
		this.usersMax = usersMax;
	}

	public ConcurrentLinkedQueue<Session> getUsers() {
		return users;
	}

}
