package org.alexdev.icarus.game.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.log.Log;

public class CatalogueManager {

	private List<CatalogueTab> parentTabs;
	private Map<Integer, List<CatalogueTab>> childTabs;
	
	private List<CataloguePage> pages;
	private List<CatalogueItem> items;
	
	public void load() {
		this.loadCatalogueTabs();
		this.loadCataloguePages();
		this.loadCatalogueItems();
	}

	public void loadCatalogueTabs() {
		
		this.parentTabs = Icarus.getDao().getCatalogue().getCatalogTabs(-1);
		this.childTabs = new HashMap<Integer, List<CatalogueTab>>();
		
		for (CatalogueTab parent : this.parentTabs) {
			List<CatalogueTab> child = Icarus.getDao().getCatalogue().getCatalogTabs(parent.getId());
			this.childTabs.put(parent.getId(), child);
		}
	}
	
	private void loadCataloguePages() {
		this.pages = Icarus.getDao().getCatalogue().getCataloguePages();
	}
	

	private void loadCatalogueItems() {
		this.items = Icarus.getDao().getCatalogue().getCatalogueItems();
	}

	public List<CatalogueTab> getParentTabs() {
		return parentTabs;
	}
	
	public List<CatalogueTab> getChildTabs(int parentId) {
		return this.childTabs.get(parentId);
	}

	public CataloguePage getPage(int pageId) {
		try {
			return this.pages.stream().filter(page -> page.getId() == pageId).findFirst().get();
		} catch (Exception e) {
			Log.exception(e);
			return null;
		}
	}
	
	public List<CatalogueItem> getPageItems(int pageId) {
		
		try {
			return this.items.stream().filter(item -> item.getPageId() == pageId && item.getData() != null).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<CatalogueItem>();
		}
	}

	public List<CatalogueItem> getItems() {
		return items;
	}
}
