package org.alexdev.icarus.game.furniture;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.log.Log;

public class FurnitureManager {

	private List<Furniture> furniture;

	public void load() {
		this.furniture = Icarus.getDao().getFurniture().getFurniture();
	}

	public Furniture getFurnitureById(int id) {
		try {
			return this.furniture.stream().filter(furni -> furni.getId() == id).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Furniture> getFurniture() {
		return furniture;
	}
}
