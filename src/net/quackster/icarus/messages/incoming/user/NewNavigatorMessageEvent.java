package net.quackster.icarus.messages.incoming.user;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class NewNavigatorMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		System.out.println("TOPKEKXDD");

		Response response = new Response();
		response.init(Outgoing.NavigatorMetaDataComposer);
		response.appendInt32(4);
		response.appendString("official_view");
		response.appendInt32(0);
		response.appendString("hotel_view");
		response.appendInt32(0);
		response.appendString("roomads_view");
		response.appendInt32(0);
		response.appendString("myworld_view");
		response.appendInt32(0);
		session.send(response);

		response.init(Outgoing.NavigatorLiftedRoomsComposer);
		response.appendInt32(0);
		/*foreach (NavigatorHeader navHeader in _navigatorHeaders)
        {
            response.appendInt32(navHeader.RoomId);
            response.appendInt32(0);
            response.appendString(navHeader.Image);
            response.appendString(navHeader.Caption);
        }*/
		session.send(response);

		response.init(Outgoing.NavigatorCategories);
		response.appendInt32(4);//eger(FlatCatsCount + 4);
		//foreach (FlatCat flat in PrivateCategories.Values) response.appendString(string.Format("category__{0}", flat.Caption));
		response.appendString("recommended");
		response.appendString("new_ads");
		response.appendString("staffpicks");
		response.appendString("official");
		session.send(response);

		response.init(Outgoing.NavigatorSavedSearchesComposer);
		response.appendInt32(0);

		//session.GetHabbo().NavigatorLogs.Count);
		/*foreach (NaviLogs navi in session.GetHabbo().NavigatorLogs.Values)
        {
            searches.AppendInteger(navi.Id);
            searches.AppendString(navi.Value1);
            searches.AppendString(navi.Value2);
            searches.AppendString("");
        }*/
		session.send(response);
		//session.SendMessage(searches);
		//session.SendMessage(SerlializeNewNavigator("official", "", session));

		response.init(Outgoing.NewNavigatorSizeMessageComposer);
        response.appendInt32(0);//pref.NewnaviX);
        response.appendInt32(0);//pref.NewnaviY);
        response.appendInt32(580);//pref.NewnaviWidth);
        response.appendInt32(600);//pref.NewnaviHeight);
        response.appendBoolean(true);
        response.appendInt32(1);

        session.send(response);

		/*@MessageEvent(messageId = EClientMessage.NavigatorGetFlatCategoriesMessageEvent)
    public static void eventHandler(Session session, ClientMessage message) {
        session.sendMessage(FlatCategoriesMessageComposer.compose());*/

		Response msg = new Response(Outgoing.FlatCategoriesMessageComposer);

		String[] categories = {
				"No Category",
				"All Other Rooms",
				"School, Daycare & Adoption Rooms",
				"Help Centre, Guide & Service Rooms",
				"Hair Salons & Modelling Rooms",
				"Gaming & Race Rooms",
				"Trading & Shopping Rooms",
				"Maze & Theme Park Rooms",
				"Chat, Chill & Discussion Rooms",
				"Club & Group Rooms",
				"Restaurant, Bar & Night Club Rooms",
				"Themed & RPG Rooms",
				"Habbo Staff Rooms"
		};

		msg.appendInt32(categories.length);

		int index = 1;
		for (String category : categories) {
			msg.appendInt32(index++);
			msg.appendString(category);
			msg.appendBoolean(true); // show category?
					msg.appendBoolean(false); // no idea
					msg.appendString("NONE");
					msg.appendString("");
					msg.appendBoolean(false);
		}
		session.send(msg);



		/*response = new Response(Outgoing.EnableNotificationsMessageComposer);
		response.appendBoolean(true); //isOpen
		response.appendBoolean(false);
		session.send(response);*/

		/*response.init(Outgoing.EnableTradingMessageComposer);
		response.appendBoolean(true);
		session.send(response);
		session.send(response);*/

		/*response.init(Outgoing.BuildersClubMembershipMessageComposer);
		response.appendInt32(0); // need to check this
		response.appendInt32(24);
		response.appendInt32(2);
		session.send(response);*/

		response.init(Outgoing.SendPerkAllowancesMessageComposer);
		response.appendInt32(11);
		response.appendString("BUILDER_AT_WORK");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("VOTE_IN_COMPETITIONS");
		response.appendString("requirement.unfulfilled.helper_level_2");
		response.appendBoolean(false);
		response.appendString("USE_GUIDE_TOOL");
		response.appendString("requirement.unfulfilled.helper_level_4");
		response.appendBoolean(true); // floorplan save button ? (Session.GetHabbo().TalentStatus == "helper" && Session.GetHabbo().CurrentTalentLevel >= 4) || (Session.GetHabbo().Rank >= 4)
		response.appendString("JUDGE_CHAT_REVIEWS");
		response.appendString("requirement.unfulfilled.helper_level_6");
		response.appendBoolean(false);
		response.appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
		response.appendString("");
		response.appendBoolean(true);
		response.appendString("CALL_ON_HELPERS");
		response.appendString("");
		response.appendBoolean(true);
		response.appendString("CITIZEN");
		response.appendString("");
		response.appendBoolean(true);//fff
		response.appendString("MOUSE_ZOOM");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("TRADE");
		response.appendString("requirement.unfulfilled.no_trade_lock");
		response.appendBoolean(false);
		response.appendString("CAMERA");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("NAVIGATOR_PHASE_TWO_2014");
		response.appendString("");
		response.appendBoolean(true);
		session.send(response);

		/*response.init(Outgoing.CitizenshipStatusMessageComposer);
		response.appendString("citizenship");
		response.appendInt32(1);
		response.appendInt32(4);
		session.send(response);*/

		/*response.init(Outgoing.GameCenterGamesListMessageComposer);
		response.appendInt32(1);
		response.appendInt32(18);
		response.appendString("elisa_habbo_stories");
		response.appendString("000000");
		response.appendString("ffffff");
		response.appendString("");
		response.appendString("");
		session.send(response);*/

	}

}
