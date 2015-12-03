package net.quackster.icarus.habbohotel.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.habbohotel.room.Room;
import net.quackster.icarus.netty.readers.Response;

public class Navigator {

	public static void SerializeSearchResultListStatics(String staticId, boolean direct, Response response, Session session) {

		if ((staticId.length() == 0) || staticId == "official") {
			staticId = "official_view";
		}

		if (staticId != "hotel_view" && staticId != "roomads_view" && staticId != "myworld_view" && !staticId.startsWith("category__") && staticId != "official_view")
		{
			response.appendString(staticId); // code
			response.appendString(""); // title
			response.appendInt32(1); // 0 : no button - 1 : Show More - 2 : Show Back button
			response.appendBoolean(staticId != "my" && staticId != "popular" && staticId != "official-root"); // collapsed
			response.appendInt32(staticId == "official-root" ? 1 : 0); // 0 : list - 1 : thumbnail
		}
		
		response.appendInt32(0);
		
		switch (staticId)
		{
		case "hotel_view":
		{
			SerializeSearchResultListStatics("popular", false, response, session);
			break;
		}
		case "myworld_view":
		{
			SerializeSearchResultListStatics("my", false, response, session);
			SerializeSearchResultListStatics("favorites", false, response, session);
			SerializeSearchResultListStatics("my_groups", false, response, session);
			SerializeSearchResultListStatics("history", false, response, session);
			SerializeSearchResultListStatics("friends_rooms", false, response, session);
			break;
		}
		case "roomads_view":
		{

			response.appendString("new_ads");
			response.appendString("Test");
			response.appendInt32(0);
			response.appendBoolean(true);
			response.appendInt32(-1);
			
			//count
			response.appendInt32(0);
			SerializeSearchResultListStatics("top_promotions", false, response, session);
			
			break;
		}
        case "official_view":
        {
            SerializeSearchResultListStatics("official-root", false, response, session);
            SerializeSearchResultListStatics("staffpicks", false, response, session);
            break;
        }
        case "official-root":
        {
            //message.AppendServerMessage(AzureEmulator.GetGame().GetNavigator().NewPublicRooms);
            break;
        }
        case "staffpicks":
        {
            //message.AppendServerMessage(AzureEmulator.GetGame().GetNavigator().NewStaffPicks);
            break;
        }
		case "my":
		{
			//response.appendInt32(0);
			new Room().serialiseNavigatorListing(response, true);
			break;
		}
		case "favorites":
		{
			response.appendInt32(0);
			//new Room().serialiseNavigatorListing(response);
			break;
		}
		case "friends_rooms":
		{
			response.appendInt32(0);
			//new Room().serialiseNavigatorListing(response);
			break;
		}
		case "recommended":
		{
			response.appendInt32(0);
			//new Room().serialiseNavigatorListing(response);
			break;
		}
		case "popular":
		{
			response.appendInt32(0);
			//new Room().serialiseNavigatorListing(response);
			break;
		}
		case "top_promotions":
		{
			response.appendInt32(0);
			//new Room().serialiseNavigatorListing(response);
			break;
		}
		default:
		{
			response.appendInt32(0);
			break;
		}
		}
	}

	public static int getNewNavigatorLength(String value) {

		switch (value) {
		case "official_view":
			return 2;

		case "myworld_view":
			return 2;

		case "hotel_view":
		case "roomads_view":
			return Icarus.PublicCount;// + 1;
		}

		return 1;
	}
}
