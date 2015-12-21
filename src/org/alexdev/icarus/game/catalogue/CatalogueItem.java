package org.alexdev.icarus.game.catalogue;

public class CatalogueItem {

	private int id;
	private int pageId;
	private String[] itemIds;
	private String catalogueName;
	private int costCredits;
	private int costPixels;
	private int costSnow;
	private int amount;
	private int vip;
	
	public void fill(int id, int pageId, String[] itemIds, String catalogueName, int costCredits, int costPixels,
			int costSnow, int amount, int vip) {
		
		this.id = id;
		this.pageId = pageId;
		this.itemIds = itemIds;
		this.catalogueName = catalogueName;
		this.costCredits = costCredits;
		this.costPixels = costPixels;
		this.costSnow = costSnow;
		this.amount = amount;
		this.vip = vip;
	}
	public int getId() {
		return id;
	}
	public int getPageId() {
		return pageId;
	}
	public String[] getItemIds() {
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
	public int getCostSnow() {
		return costSnow;
	}
	public int getAmount() {
		return amount;
	}
	public int getVip() {
		return vip;
	}
	
	
}
