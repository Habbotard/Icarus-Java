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

	public Object getUsername() {
		return "Alex";
	}

	public Object getMotto() {
		return "melons";
	}

	public Object getFigure() {
		// TODO Auto-generated method stub
		return "fa-1201-0.lg-270-91.hd-180-1.sh-300-91.ch-805-84.hr-125-42";
	}

}
