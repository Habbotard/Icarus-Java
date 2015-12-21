package org.alexdev.icarus.game.catalogue;

public class CataloguePage {

	private int id;
	private String layout;
	private String headline;
	private String teaser;
	private String special;
	private String text1;
	private String text2;
	private String textDetails;
	private String textTeaser;
	private boolean vip;
	private String linkDescription;
	private String linkPageName;
	
	public void fill(int id, String pageLayout, String pageHeadline, String pageTeaser, String pageSpecial,
			String pageText1, String pageText2, String pageTextDetails, String pageTextTeaser, boolean vip,
			String pageLinkDescription, String pageLinkComments) {
		
		this.id = id;
		this.layout = pageLayout;
		this.headline = pageHeadline;
		this.teaser = pageTeaser;
		this.special = pageSpecial;
		this.text1 = pageText1;
		this.text2 = pageText2;
		this.textDetails = pageTextDetails;
		this.textTeaser = pageTextTeaser;
		this.vip = vip;
		this.linkDescription = pageLinkDescription;
		this.linkPageName = pageLinkComments;
	}

	public boolean isLayout(String layout) {
		return this.layout.equalsIgnoreCase(layout);
	}
	
	public int getId() {
		return id;
	}

	public String getLayout() {
		return layout;
	}

	public String getHeadline() {
		return headline;
	}

	public String getTeaser() {
		return teaser;
	}

	public String getSpecial() {
		return special;
	}

	public String getText1() {
		return text1;
	}

	public String getText2() {
		return text2;
	}

	public String getTextDetails() {
		return textDetails;
	}

	public String getTextTeaser() {
		return textTeaser;
	}

	public boolean isVip() {
		return vip;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public String getLinkComments() {
		return linkPageName;
	}
	
	
}
