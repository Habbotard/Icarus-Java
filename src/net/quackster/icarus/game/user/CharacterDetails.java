package net.quackster.icarus.game.user;

public class CharacterDetails {

	private boolean authenticated;
	
	public CharacterDetails() {
		this.authenticated = false;
	}
	
	public int getId() {
		return 1;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

}
