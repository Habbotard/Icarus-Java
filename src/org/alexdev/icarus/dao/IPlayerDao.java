package org.alexdev.icarus.dao;

import org.alexdev.icarus.game.user.CharacterDetails;
import org.alexdev.icarus.game.user.Session;

public interface IPlayerDao {

	public CharacterDetails getDetails(int userId);
	public boolean login(Session session, String ssoTicket);
	public int getId(String username);
}
