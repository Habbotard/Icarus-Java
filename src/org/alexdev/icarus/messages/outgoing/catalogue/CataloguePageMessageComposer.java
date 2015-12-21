package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class CataloguePageMessageComposer extends Response {

	public CataloguePageMessageComposer(CataloguePage page, String type) {
		
		this.init(Outgoing.CataloguePageMessageComposer);
		this.appendInt32(page.getId());
		
		if (page.isLayout("frontpage")) {
			
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
			this.appendString(page.getLayout());
			this.appendInt32(3);
			this.appendString(page.getHeadline());
			this.appendString(page.getTeaser());
			this.appendString(page.getSpecial());
			this.appendInt32(4);
			this.appendString(page.getText1());
			//this.appendString(page.getText1());
			this.appendString(page.getText2());
			this.appendString(page.getTextTeaser());
			//this.appendString(page.getTextDetails());
		}
		
		this.appendInt32(0); // TODO: Catalogue items
		
		this.appendInt32(-1);
		this.appendBoolean(false);
	}


}
