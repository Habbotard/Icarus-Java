package net.quackster.icarus.game.room;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.RoomDao;
import net.quackster.icarus.game.room.models.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.netty.readers.Response;
import net.quackster.icarus.netty.readers.SerialiseType;

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
	private List<Session> users;

	public Room(ResultSet row) throws SQLException {

		this.users = new ArrayList<Session>();
		
		this.id = row.getInt("id");
		this.ownerId = row.getInt("owner_id");
		this.ownerName = "";
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
		
		System.out.println("ROOM ID: " + id);
		
	}

	public void serialise(Response response, SerialiseType type ) {

		if (type == SerialiseType.ROOM_NAVIGATOR) {
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
			response.appendInt32(0);
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

	public List<Session> getUsers() {
		return users;
	}

}
