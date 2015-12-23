package org.alexdev.icarus.game.user;

import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.messages.outgoing.user.CreditsBalanceMessageComposer;

public class CharacterDetails {

	private int id = -1;
	private String username = "Alex";
	private String motto = "banana man";
	private String figure;
	private int rank;
	private int credits;
	
	private boolean authenticated;
	private IEntity entity;
	
	public CharacterDetails(IEntity session) {
		this.authenticated = false;
		this.entity = session;
	}
	
	public void fill(int id, String username, String motto, String figure, int rank, int credits) {
		
		this.id = id;
		this.username = username;
		this.motto = motto;
		this.figure = figure;
		this.rank = rank;
		this.credits = credits;
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

	public int getRank() {
		return rank;
	}

	public void setCredits(int newTotal, boolean sendUpdate) {
		this.credits = newTotal;
		
		if (sendUpdate) {
			
			if (this.entity instanceof Session) {
			
				Session session = (Session)this.entity;
				session.send(new CreditsBalanceMessageComposer(this.credits));
			
			}
		}
	}
	
	public int getCredits() {
		return credits;
	}
}
