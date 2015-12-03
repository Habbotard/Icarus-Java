package net.quackster.icarus.habbohotel.navigator;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.habbohotel.room.Room;
import net.quackster.icarus.netty.readers.Response;

public class Navigator {

	public static void serializeNavigatorList(String staticId, Response response, Session session) {

		if ((staticId.length() == 0) || staticId.equals("official")) {
			staticId = "official_view";
		}

		if (!staticId.equals("hotel_view") && !staticId.equals("roomads_view") && !staticId.equals("myworld_view") && !staticId.startsWith("category__") && !staticId.equals("official_view"))
		{
			response.appendString(staticId); // code
			response.appendString(""); // title
			response.appendInt32(1); // 0 : no button - 1 : Show More - 2 : Show Back button
			response.appendBoolean(!staticId.equals("my") && !staticId.equals("popular") && !staticId.equals("official-root")); // collapsed
			response.appendInt32(staticId.equals("official-root") ? 1 : 0); // 0 : list - 1 : thumbnail
		}

		switch (staticId)
		{
		case "hotel_view":
		{
			serializeNavigatorList("popular", response, session);
			break;
		}
		case "myworld_view":
		{
			serializeNavigatorList("my", response, session);
			serializeNavigatorList("favorites", response, session);
			serializeNavigatorList("my_groups", response, session);
			serializeNavigatorList("history", response, session);
			serializeNavigatorList("friends_rooms", response, session);
			break;
		}
		case "roomads_view":
		{
			serializeNavigatorList("top_promotions", response, session);

			break;
		}
		case "official_view":
		{
			serializeNavigatorList("official-root", response, session);
			serializeNavigatorList("staffpicks", response, session);
			break;
		}
		}
		
		// TODO: Handle room request here from tag ID, eg: top_promotions jajaja
		response.appendInt32(0);
	}

	public static int getNewNavigatorLength(String value) {

		switch (value) {
		case "official_view":
			return 2;

		case "myworld_view":
			return 5;

		case "hotel_view":
			return 1;
		case "roomads_view":
			return 1;
		}

		return 1;
	}
}