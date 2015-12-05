package net.quackster.icarus.game.room;

import net.quackster.icarus.netty.readers.Response;

public class Room {

	private int Id = 1;
	private int OwnerId = 23;
	private String Name = "ROOMXD";
	private String Owner = "topkek";
	private int State = 0;
	private int UsersNow = 34;
	private int UsersMax = 40;
	private String Description = "descccc";
	private int TradeState = 0;
	private int Score = 0;
	private int Category = 1;
	private String tab;

	public Room(String name) {
		this.Name = name;
	}

	public Room() {
		// TODO Auto-generated constructor stub
	}

	public void serialiseNavigatorListing(Response response, Boolean enterRoom) {
		
        response.appendInt32(Id);
        response.appendString(Name);
        response.appendInt32(OwnerId);
        response.appendString(Owner);
        response.appendInt32(State);
        response.appendInt32(UsersNow);
        response.appendInt32(UsersMax);
        response.appendString(Description);
        response.appendInt32(TradeState);
        response.appendInt32(Score);
        response.appendInt32(0); // Ranking
        response.appendInt32(Category);

        response.appendInt32(0); //TagCount
        
        int enumType = enterRoom ? 32 : 0;
        
        /*PublicItem publicItem = AzureEmulator.GetGame().GetNavigator().GetPublicItem(Id);
        if (publicItem != null && !string.IsNullOrEmpty(publicItem.Image))
        {
            imageData = publicItem.Image;
            enumType += 1;
        }*/

        //if (Group != null) enumType += 2;
       // if (showEvents && Event != null) enumType += 4;
       // if (Type == "private") enumType += 8;
        //if (AllowPets) enumType += 16;
        response.appendInt32(enumType);
        
        /*foreach (string current in Tags) response.AppendString(current);

        string imageData = null;

        Int32 enumType = enterRoom ? 32 : 0;
        PublicItem publicItem = AzureEmulator.GetGame().GetNavigator().GetPublicItem(Id);
        if (publicItem != null && !string.IsNullOrEmpty(publicItem.Image))
        {
            imageData = publicItem.Image;
            enumType += 1;
        }

        if (Group != null) enumType += 2;
        if (showEvents && Event != null) enumType += 4;
        if (Type == "private") enumType += 8;
        if (AllowPets) enumType += 16;
        response.AppendInteger(enumType);

        if (imageData != null)
        {
            response.AppendString(imageData);
        }
        if (Group != null)
        {
            response.AppendInteger(Group.Id);
            response.AppendString(Group.Name);
            response.AppendString(Group.Badge);
        }
        if (showEvents && Event != null)
        {
            response.AppendString(Event.Name);
            response.AppendString(Event.Description);
            response.AppendInteger((int)Math.Floor((Event.Time - AzureEmulator.GetUnixTimeStamp()) / 60.0));
        }*/
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

}
