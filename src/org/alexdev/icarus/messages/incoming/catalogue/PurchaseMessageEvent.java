package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.netty.readers.Request;

public class PurchaseMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		int pageId = request.readInt();
		int itemId = request.readInt();
		/*String extraData = */request.readString();
		int priceAmount = request.readInt();
		
		CataloguePage page = Icarus.getGame().getCatalogue().getPage(pageId);
		
		if (page.getMinRank() > session.getDetails().getRank()) {
			return;
		}
		
		CatalogueItem item = Icarus.getGame().getCatalogue().getItem(itemId);
		
		if (item == null) {
			return;
		}
		
		int finalAmount = priceAmount;
		
		if (priceAmount > 5) {
			
			int discount = ((int) Math.floorDiv(priceAmount, 6) * 6);
			
			int freeItems = (discount - 3) / 3;
			
			if (priceAmount >= 42) {
				freeItems++; // add another free item if more than 42 items 8)
			}
			
			if (priceAmount >= 99) { // not divisible by 3
				freeItems = 33;
			}
			
			finalAmount = priceAmount - freeItems;
		}

		// TODO: Habbo club check
		
		if (item.isLimited() && item.getLimitedStack() <= item.getLimitedSells()) {
			// TODO: Alert deal soldout
			return;
		} 
		
		if (item.isLimited()) {
			
			// TODO: Allow possible chance that everyone is allowed to buy one rare
		}
		
		boolean creditsError = false;
		
		// TODO: Pixel error
		if (session.getDetails().getCredits() < (item.getCostCredits() * finalAmount)) {
			
			session.send(new PurchaseErrorMessageComposer(creditsError, false));
			return;
		}
		
		// TODO: Seasonal currency error?
		
		if (item.getCostCredits() > 0) {
			session.getDetails().setCredits(session.getDetails().getCredits() - item.getCostCredits(), true);
		}
		
		// TODO: Item badges
		// TODO: Limited sales update
		
		session.send(new PurchaseNotificationMessageComposer(item, finalAmount));
		
		// TODO: Add items to players inventory
	}

}
