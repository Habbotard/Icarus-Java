package net.quackster.icarus.game.room;

import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.model.RoomModel;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.netty.readers.Response;

public class RoomData {

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
	private String roomType = "private";
	private boolean allowPets;
	private boolean allowPetsEat;
	private boolean allowWalkthrough;
	private boolean hideWall;
	private int wallThickness;
	private int floorThickness;
	private String tagFormat;
	
	private List<Integer> rights;
	private Room room;
	
	public RoomData(Room room) {
		
		this.room = room;
		this.rights = new ArrayList<Integer>();
	}
	
	public void fill(int id, int ownerId, String ownerName, String name, int state, int usersNow, int usersMax,
			String description, int tradeState, int score, int category, int groupId, String model, String wall,
			String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
			boolean hideWall, int wallThickness, int floorThickness, String tagFormat) {

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
	
	public void serialise(Response response) {
		this.serialise(response, false);
	}

	public void serialise(Response response, boolean enterRoom) {
		
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

		int enumType = enterRoom ? 32 : 0;
		
		// if has event
		//enumType += 4;

		if (this.roomType.equals("private")) {
			enumType += 8;
		}

		if (this.allowPets) { 
			enumType += 16;
		}

		response.appendInt32(enumType);
		
		//response.appendString("Hello");
        //response.appendString("xd lolz");
        //response.appendInt32(100);
        

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

		if (this.room.getUsers() == null) {
			this.room.setUsers(new ArrayList<Session>());
		}

		this.usersNow = this.room.getUsers().size();
		return usersNow;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public RoomModel getModel() {
		return Icarus.getDao().getRoom().getModel(this.model);
	}

	public String getTagFormat() {
		return tagFormat;
	}
	
	public List<Integer> getRights() {
		return rights;
	}

	public void dispose() {

		this.name = null;
		this.ownerName = null;
		this.description = null;
		this.tagFormat = null;
		this.landscape = null;
		this.model = null;
		this.wall = null;
		
	}

}
