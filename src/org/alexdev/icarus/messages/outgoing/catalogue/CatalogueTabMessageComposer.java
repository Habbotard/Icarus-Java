package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CatalogueTabMessageComposer extends Response {

	public CatalogueTabMessageComposer(String type, int parentId, int rank) {
		
		List<CatalogueTab> parentTabs = Icarus.getGame().getCatalogue().getParentTabs(rank);
		
		this.init(Outgoing.CatalogueTabMessageComposer);
		this.appendBoolean(true);
		this.appendInt32(0);
		this.appendInt32(-1);
		this.appendString("root");
		this.appendString("");
		this.appendInt32(0);
		this.appendInt32(parentTabs.size());
		
		for (CatalogueTab parentTab : parentTabs) {
			
			this.appendBoolean(parentTab.isEnabled());
			this.appendInt32(parentTab.getIconImage());
			this.appendInt32(parentTab.getId());
			this.appendString(parentTab.getCaption().toLowerCase().replace(" ", "_"));
			this.appendString(parentTab.getCaption());
			this.appendInt32(0); // TODO: flat offers
			
			List<CatalogueTab> childTabs = Icarus.getGame().getCatalogue().getChildTabs(parentTab.getId(), rank);
			
			this.appendInt32(childTabs.size());
			
			for (CatalogueTab childTab : childTabs) {
				
				this.appendBoolean(childTab.isEnabled());
				this.appendInt32(childTab.getIconImage());
				this.appendInt32(childTab.getId());
				this.appendString(childTab.getCaption().toLowerCase().replace(" ", "_"));
				this.appendString(childTab.getCaption());
				this.appendInt32(0);
				this.appendInt32(0);
			}
		}
		
		this.appendBoolean(false);
		this.appendString(type);
	}
}
