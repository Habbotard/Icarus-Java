package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class PurchaseNotificationMessageComposer extends Response {

	public PurchaseNotificationMessageComposer(CatalogueItem item, int amount) {
		
		this.init(Outgoing.PurchaseNotificationComposer);
		this.appendInt32(item.getData().getId());
		this.appendString(item.getData().getItemName());
		this.appendBoolean(false);
		this.appendInt32(item.getCostCredits());
		this.appendInt32(item.getCostPixels());
		this.appendInt32(0);
		this.appendBoolean(true);
		
		this.appendInt32(1); // amount
		
		this.appendString(item.getData().getType());
		this.appendInt32(item.getData().getSpriteId());
		this.appendString(item.getExtraData());
		this.appendInt32(amount);
		
		this.appendBoolean(item.isLimited());
		
		if (item.isLimited()) {
			this.appendInt32(item.getLimitedStack());
			this.appendInt32(item.getLimitedSells());
		}
		
		this.appendString(item.getSubscriptionStatus());
		this.appendInt32(1);
	}

}
