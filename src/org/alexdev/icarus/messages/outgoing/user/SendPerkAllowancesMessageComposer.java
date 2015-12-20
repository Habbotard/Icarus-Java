package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class SendPerkAllowancesMessageComposer extends Response {
	
	public SendPerkAllowancesMessageComposer() {
		this.init(Outgoing.SendPerkAllowancesMessageComposer);
		this.appendInt32(11);
		this.appendString("BUILDER_AT_WORK");
		this.appendString("");
		this.appendBoolean(false);
		this.appendString("VOTE_IN_COMPETITIONS");
		this.appendString("requirement.unfulfilled.helper_level_2");
		this.appendBoolean(false);
		this.appendString("USE_GUIDE_TOOL");
		this.appendString("requirement.unfulfilled.helper_level_4");
		this.appendBoolean(true); // floorplan save button ? (Session.GetHabbo().TalentStatus == "helper" && Session.GetHabbo().CurrentTalentLevel >= 4) || (Session.GetHabbo().Rank >= 4)
		this.appendString("JUDGE_CHAT_REVIEWS");
		this.appendString("requirement.unfulfilled.helper_level_6");
		this.appendBoolean(false);
		this.appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
		this.appendString("");
		this.appendBoolean(true);
		this.appendString("CALL_ON_HELPERS");
		this.appendString("");
		this.appendBoolean(true);
		this.appendString("CITIZEN");
		this.appendString("");
		this.appendBoolean(true);//fff
		this.appendString("MOUSE_ZOOM");
		this.appendString("");
		this.appendBoolean(false);
		this.appendString("TRADE");
		this.appendString("requirement.unfulfilled.no_trade_lock");
		this.appendBoolean(false);
		this.appendString("CAMERA");
		this.appendString("");
		this.appendBoolean(false);
		this.appendString("NAVIGATOR_PHASE_TWO_2014");
		this.appendString("");
		this.appendBoolean(true);
	}
}
