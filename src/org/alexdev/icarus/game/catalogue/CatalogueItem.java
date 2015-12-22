package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.furniture.Furniture;

public class CatalogueItem {

	private int id;
	private int pageId;
	private int itemIds;
	private String catalogueName;
	private int costCredits;
	private int costPixels;
	private int costBelCredits;
	private int amount;
	private int subscriptionStatus;
	private int questType;
	private int songId;
	private String extraData;
	private String badge;
	
	private int limitedStack;
	private int limitedSells;
	
	private boolean hasOffer;

	/*				item.fill(row.getInt("id"), row.getInt("page_id"), row.getInt("item_ids"), row.getString("catalog_name"), 
							row.getInt("cost_credits"), row.getInt("cost_pixels"), row.getInt("cost_belcredits"), row.getInt("amount"), 
							row.getInt("item_subscription_status"), row.getInt("quest_type"), row.getInt("song_id"), row.getString("extradata"),
							row.getString("badge"), row.getInt("limited_stack"), row.getInt("limited_sells"), row.getInt("offer_active") == 1);*/
	public void fill(int id, int pageId, int itemIds, String catalogueName, int costCredits, int costBelCredits, int costDuckets, 
						int amount, int subscriptionStatus, int songId, String extraData, String badage, int limitedStack, int limitedSells, 
						boolean hasOffer) {

		this.id = id;
		this.pageId = pageId;
		this.itemIds = itemIds;
		this.catalogueName = catalogueName;
		this.costCredits = costCredits;
		this.costPixels = costDuckets;
		this.costBelCredits = costBelCredits;
		this.amount = amount;
		this.subscriptionStatus = subscriptionStatus;
		this.songId = songId;
		this.extraData = extraData;
		this.badge = badage;
		this.limitedStack = limitedStack;
		this.limitedSells = limitedSells;
		this.hasOffer = hasOffer;
	}

	public Furniture getData() {
		return Icarus.getGame().getFurniture().getFurnitureById(this.itemIds);
	}
	
	public int getId() {
		return id;
	}
	public int getPageId() {
		return pageId;
	}
	
	public int getSpriteId() {
		return itemIds;
	}
	public String getCatalogueName() {
		return catalogueName;
	}
	
	public int getCostCredits() {
		return costCredits;
	}
	public int getCostPixels() {
		return costPixels;
	}

	public int getAmount() {
		return amount;
	}

	public int getCostBelCredits() {
		return costBelCredits;
	}
	
	public int getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public int getSongId() {
		return songId;
	}

	public String getExtraData() {
		return extraData;
	}

	public String getBadge() {
		return badge;
	}
	
	public boolean isLimited() {
		return this.limitedStack > 0;
	}

	public int getLimitedStack() {
		return limitedStack;
	}

	public int getLimitedSells() {
		return limitedSells;
	}

	public boolean isHasOffer() {
		return hasOffer;
	}

}
