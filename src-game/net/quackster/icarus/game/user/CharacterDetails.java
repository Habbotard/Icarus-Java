package net.quackster.icarus.game.user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterDetails {

	private int id = -1;
	private String username = "Alex";
	private String motto = "banana man";
	private String figure = "fa-1201-0.lg-270-91.hd-180-1.sh-300-91.ch-805-84.hr-125-42";
	private boolean authenticated;
	
	public CharacterDetails() {
		this.authenticated = false;
	}
	
	public void fill(ResultSet row) throws SQLException {
		
		this.id = row.getInt("id");
		this.username = row.getString("username");
		this.motto = row.getString("motto");
		this.figure = row.getString("figure");
		this.authenticated = true;
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
