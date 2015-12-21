package org.alexdev.icarus.game.furniture;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;

public class Furniture {

	private int id;
	private String publicName;
	private String itemName;
	private String type;
	private int width;
	private int length;
	private double stackHeight;
	private boolean canStack;
	private boolean canSit;
	private boolean isWalkable;
	private int spriteId;
	private boolean allowRecycle;
	private boolean allowTrade;
	private boolean allowMarketplaceSell;
	private boolean allowGift;
	private boolean allowInventoryStack;
	private InteractionType interactionType;
	private int interationModes;
	private String[] vendingIds;
	
	public void fill (int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
			boolean canStack, boolean canSit, boolean isWalkable, int spriteId, boolean allowRecycle,
			boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack,
			InteractionType interactionType, int interationModes, String[] vendingIds) {

		this.id = id;
		this.publicName = publicName;
		this.itemName = itemName;
		this.type = type;
		this.width = width;
		this.length = length;
		this.stackHeight = stackHeight;
		this.canStack = canStack;
		this.canSit = canSit;
		this.isWalkable = isWalkable;
		this.spriteId = spriteId;
		this.allowRecycle = allowRecycle;
		this.allowTrade = allowTrade;
		this.allowMarketplaceSell = allowMarketplaceSell;
		this.allowGift = allowGift;
		this.allowInventoryStack = allowInventoryStack;
		this.interactionType = interactionType;
		this.interationModes = interationModes;
		this.vendingIds = vendingIds;
	}

	public int getId() {
		return id;
	}

	public String getPublicName() {
		return publicName;
	}

	public String getItemName() {
		return itemName;
	}

	public String getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public double getStackHeight() {
		return stackHeight;
	}

	public boolean isCanStack() {
		return canStack;
	}

	public boolean isCanSit() {
		return canSit;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public int getSpriteId() {
		return spriteId;
	}

	public boolean isAllowRecycle() {
		return allowRecycle;
	}

	public boolean isAllowTrade() {
		return allowTrade;
	}

	public boolean isAllowMarketplaceSell() {
		return allowMarketplaceSell;
	}

	public boolean isAllowGift() {
		return allowGift;
	}

	public boolean isAllowInventoryStack() {
		return allowInventoryStack;
	}

	public InteractionType getInteractionType() {
		return interactionType;
	}

	public int getInterationModes() {
		return interationModes;
	}

	public String[] getVendingIds() {
		return vendingIds;
	}
	
	
	//private 
}
