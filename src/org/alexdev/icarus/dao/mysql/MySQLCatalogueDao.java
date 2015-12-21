package org.alexdev.icarus.dao.mysql;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.ICatalogueDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.mysql.Storage;

public class MySQLCatalogueDao implements ICatalogueDao, IProcessStorage<CataloguePage, ResultSet> {

	private MySQLDao dao;

	public MySQLCatalogueDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<CatalogueTab> getCatalogTabs() {
		return this.getCatalogTabs(-1);
	}
	
	@Override
	public List<CatalogueTab> getCatalogTabs(int parentId) {
		
		List<CatalogueTab> tabs = new ArrayList<CatalogueTab>();
		
		ResultSet row = null;
		
		try {
			
			row = this.dao.getStorage().getTable("SELECT * FROM catalogue_pages WHERE parent_id = " + parentId);
			
			while (row.next()) {
				
				CatalogueTab tab = new CatalogueTab();
				
				tab.fill(row.getInt("id"), row.getInt("parent_id"), row.getString("caption"), row.getInt("icon_colour"), 
							row.getInt("icon_image"), /*row.getInt("enabled") == 1*/true, row.getInt("min_rank"));
				
				tabs.add(tab);
			}
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}
		
		return tabs;
	}
	
	@Override
	public List<CataloguePage> getCataloguePages() {
		
		List<CataloguePage> pages = new ArrayList<CataloguePage>();
		
		ResultSet row = null;
		
		try {
			
			row = this.dao.getStorage().getTable("SELECT * FROM catalogue_pages");
			
			while (row.next()) {
				
				CataloguePage page = new CataloguePage();
				this.fill(page, row);
				pages.add(page);
			}
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}
		
		return pages;
	}
	

	@Override
	public List<CatalogueItem> getCatalogueItems() {

		List<CatalogueItem> items = new ArrayList<CatalogueItem>();
		
		ResultSet row = null;
		
		try {
			
			row = this.dao.getStorage().getTable("SELECT * FROM catalogue_items");
			
			while (row.next()) {
				
				CatalogueItem item = new CatalogueItem();
				
				item.fill(row.getInt("id"), row.getInt("page_id"), row.getString("item_ids").split(","), row.getString("catalog_name"), 
							row.getInt("cost_credits"), row.getInt("cost_pixels"), row.getInt("cost_snow"), row.getInt("amount"), row.getInt("vip"));
				
				items.add(item);
			}
			
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.releaseObject(row);
		}
		
		return items;
	}
	

	@Override
	public CataloguePage fill(CataloguePage instance, ResultSet row) throws Exception {

		instance.fill(row.getInt("id"), row.getString("page_layout"), row.getString("page_headline"), row.getString("page_teaser"),
				row.getString("page_special"), row.getString("page_text1"), row.getString("page_text2"), row.getString("page_text_details"), 
				row.getString("page_text_teaser"), row.getBoolean("vip_only"), row.getString("page_link_description"), row.getString("page_link_pagename"));
	
		
		return instance;
	
	}

}
