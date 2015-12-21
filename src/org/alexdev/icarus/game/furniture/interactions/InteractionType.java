package org.alexdev.icarus.game.furniture.interactions;

public enum InteractionType {

	DEFAULT(null),
	GATE(null),
	POSTIT(null),
	ROOMEFFECT(null),
	DIMMER(null),
	TROPHY(null),
	BED(null),
	SCOREBOARD(null),
	VENDINGMACHINE(null),
	ALERT(null),
	ONEWAYGATE(null),
	LOVESHUFFLER(null),
	HABBOWHEEL(null),
	DICE(null),
	BOTTLE(null),
	TELEPORT(null),
	RENTALS(null),
	PET(null),
	ROLLER(null),
	WATER(null),
	BALL(null),
	BB_RED_GATE(null),
	BB_GREEN_GATE(null),
	BB_YELLOW_GATE(null),
	BB_PUCK(null),
	BB_BLUE_GATE(null),
	BB_PATCH(null),
	BB_TELEPORT(null),
	BLUE_SCORE(null),
	GREEN_SCORE(null),
	RED_SCORE(null),
	YELLOW_SCORE(null),
	FBGATE(null),
	TAGPOLE(null),
	COUNTER(null),
	RED_GOAL(null),
	BLUE_GOAL(null),
	YELLOW_GOAL(null),
	GREEN_GOAL(null),
	WIRED(null),
	WF_TRG_ONSAY(null),
	WF_ACT_SAYMSG(null),
	WF_TRG_ENTERROOM(null),
	WF_ACT_MOVEUSER(null),
	WF_ACT_TOGGLEFURNI(null),
	WF_TRG_FURNISTATE(null),
	WF_TRG_ONFURNI(null),
	PRESSURE_PAD(null),
	WF_TRG_OFFFURNI(null),
	WF_TRG_GAMEEND(null),
	WF_TRG_GAMESTART(null),
	WF_TRG_TIMER(null),
	WF_ACT_GIVEPOINTS(null),
	WF_TRG_ATTIME(null),
	WF_TRG_ATSCORE(null),
	WF_ACT_MOVEROTATE(null),
	ROLLERSKATE(null),
	STICKIEPOLE(null),
	WF_XTRA_RANDOM(null),
	WF_CND_TRGGRER_ON_FRN(null),
	WF_CND_FURNIS_HV_AVTRS(null),
	WF_ACT_MATCHFURNI(null),
	WF_CND_HAS_FURNI_ON(null),
	PUZZLEBOX(null),
	SWITCH(null);

	private Interaction interaction;
	
	InteractionType(Interaction interaction) {
		this.interaction = interaction;
	}
	
	public Interaction getInteraction() {
		return interaction;
	}
	
	public static InteractionType getType(String databaseType) {
		return InteractionType.valueOf(databaseType.toUpperCase());
	}
}
