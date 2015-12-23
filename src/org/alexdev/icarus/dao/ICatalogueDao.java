package org.alexdev.icarus.dao;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.catalogue.CatalogueTab;

public interface ICatalogueDao {

	public List<CatalogueTab> getCatalogTabs();
	public List<CatalogueTab> getCatalogTabs(int parentId);
	public List<CataloguePage> getCataloguePages();
	public List<CatalogueItem> getCatalogueItems();
}
