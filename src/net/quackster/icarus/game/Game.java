package net.quackster.icarus.game;

import net.quackster.icarus.game.navigator.Navigator;

public class Game {

	private Navigator navigator;
	
	public Game() throws Exception {
		this.navigator = new Navigator();
	}
	
	public Navigator getNavigator() {
		return navigator;
	}
}
