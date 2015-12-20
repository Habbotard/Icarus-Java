package org.alexdev.icarus.game.user;

public class CharacterDetails {

	private int id = -1;
	private String username = "Alex";
	private String motto = "banana man";
	private String figure;
	private boolean authenticated;
	
	public CharacterDetails() {
		this.authenticated = false;
	}
	
	public void fill(int id, String username, String motto, String figure) {
		
		this.id = id;
		this.username = username;
		this.motto = motto;
		this.figure = figure;
		this.authenticated = true;
	}
	
	// TODO: permissions
	public boolean hasFuse(String fuse) {
		return false;
	}
	
	public void dispose() {
		
		this.username = null;
		this.motto = null;
		this.figure = null;
	}
	
	public int getId() {
		return id;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}
	
	

}
