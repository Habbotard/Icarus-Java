package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CataloguePageMessageComposer extends Response {

	public CataloguePageMessageComposer(CataloguePage page, String type) {

		this.init(Outgoing.CataloguePageMessageComposer);
		this.appendInt32(page.getId());
		this.appendString(type);

		if (page.getLayout().toLowerCase().contains("frontpage")) {

			this.appendString("frontpage4");
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendString(page.getText2());
			this.appendInt32(0);
			this.appendInt32(-1);
			this.appendBoolean(false);

		} else if (page.isLayout("spaces") || page.isLayout("spaces_new")) {

			this.appendString("spaces_new");
			this.appendInt32(1);
			this.appendString(page.getHeadline());
			this.appendInt32(1);
			this.appendString(page.getText1());

		} else if (page.isLayout("default_3x3")) {
			
			this.appendString(page.getLayout());
			this.appendInt32(3);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendString(page.getSpecial());
			this.appendInt32(3);
			this.appendString(page.getText1());
			//this.appendString(page.getText1());
			this.appendString("");//page.getTextDetails());
			this.appendString(page.getTextTeaser());

		} else if (page.isLayout("club_buy")) {

			this.appendString("vip_buy");
			this.appendString(page.getLayout());
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(0);

		} else if (page.isLayout("club_gifts")) {

			this.appendString("club_gifts");
			this.appendString(page.getHeadline());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendInt32(0);

		} else if (page.isLayout("recycler_info")) {

			this.appendString(page.getLayout());
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(3);
			this.appendString(page.getText1());
			this.appendString(page.getText2());
			this.appendString(page.getTextDetails());

		} else if (page.isLayout("recycler_prizes")) {

			this.appendString("recycler_prizes");
			this.appendInt32(1);
			this.appendString("catalog_header_furnimatic");
			this.appendInt32(1);
			this.appendString(page.getText1());

		} else if (page.isLayout("guilds")) {

			this.appendString("guild_frontpage");
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(3);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());
			this.appendString(page.getTextTeaser());

		} else if (page.isLayout("guild_furni")) {

			this.appendString("guild_custom_furni");
			this.appendInt32(3);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendString(page.getSpecial());
			this.appendInt32(3);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());
			this.appendString(page.getTextTeaser());

		} else if (page.isLayout("soundmachine")) {

			this.appendString("soundmachine");
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());

		} else if (page.isLayout("pets")) {

			this.appendString("pets");
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(1);
			this.appendString(page.getText1());
			
		} else if (page.isLayout("bots")) {

			this.appendString(page.getLayout());
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());
			
		} else if (page.isLayout("default_3x3_color_grouping")) {

			this.appendString(page.getLayout());
			this.appendInt32(2);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());
		} else {
			
			this.appendString(page.getLayout());
			this.appendInt32(3);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendString(page.getSpecial());
			this.appendInt32(2);
			this.appendString(page.getText1());
			this.appendString(page.getTextDetails());
			this.appendString(page.getTextTeaser());
		}
		
		List<CatalogueItem> items = page.getItems();

		this.appendInt32(items.size()); 

		for (CatalogueItem item : items) {
			item.serialise(this);
		}

		this.appendInt32(-1);
		this.appendBoolean(false);
	}


}
