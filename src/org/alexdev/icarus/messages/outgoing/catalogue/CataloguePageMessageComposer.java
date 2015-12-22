package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CataloguePageMessageComposer extends Response {

	public CataloguePageMessageComposer(CataloguePage page, String type) {

		this.init(Outgoing.CataloguePageMessageComposer);
		this.appendInt32(page.getId());

		if (page.isLayout("frontpage4")) {

			this.appendString("NORMAL");
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

			this.appendString("NORMAL");
			this.appendString("spaces_new");
			this.appendInt32(1);
			this.appendString(page.getHeadline());
			this.appendInt32(1);
			this.appendString(page.getText1());

		} else if (page.isLayout("default_3x3")) {

			this.appendString("NORMAL");
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

		} else {


			this.appendString("NORMAL");
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
			//this.appendString(page.getTextDetails());
		}

		List<CatalogueItem> items = page.getItems();

		this.appendInt32(items.size()); // TODO: Catalogue items

		for (CatalogueItem item : items) {

			this.appendInt32(item.getId());
			this.appendString(item.getCatalogueName());
			this.appendBoolean(false);
			
            if (item.getCostPixels() == 0 && item.getCostCredits() == 0 && item.getCostQuests() == 0 && item.getQuestType() == 0) {
                this.appendInt32(item.getCostBelCredits());
                this.appendInt32(0);
                
            } else if (item.getQuestType() > 1) {
                this.appendInt32(0);
                this.appendInt32(item.getCostQuests());
            } else  {
                this.appendInt32(item.getCostCredits());
                this.appendInt32(item.getCostPixels());
            }

            this.appendInt32(item.getQuestType());
            
            if (item.isLimited() || item.getData().getType().equals("r")) {
            	this.appendBoolean(false);
            } else {
            	this.appendBoolean(item.getData().isAllowGift());
            }
            
            this.appendInt32(1); // is deal
            this.appendString(item.getData().getType());
            
            if (item.getBadge().length() > 0) {
            	
            	this.appendString(item.getBadge());
            	this.appendInt32(item.getSubscriptionStatus());
            	this.appendInt32(item.getAmount());
            } else {
            	
            	this.appendInt32(item.getData().getSpriteId());
            	this.appendString(item.getExtraData());
            	this.appendInt32(item.getAmount());
            	
            	this.appendBoolean(item.isLimited()); 
            	
            	if (item.isLimited()) {
            		this.appendInt32(item.getLimitedStack());
            		this.appendInt32(item.getLimitedSells());
            	}
            	
            	this.appendInt32(item.getSubscriptionStatus());
            	
            	if (item.isLimited()) {
            		this.appendBoolean(!item.isLimited() && item.isHasOffer()); // && HaveOffer
            	} else {
            		this.appendBoolean(false);
            	}
            }

		}

		this.appendInt32(-1);
		this.appendBoolean(false);
	}


}
