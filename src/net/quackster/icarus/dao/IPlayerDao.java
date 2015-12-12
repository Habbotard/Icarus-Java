package net.quackster.icarus.dao;

import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.game.user.Session;

public interface IPlayerDao {

	public CharacterDetails getDetails(int userId);
	public boolean login(Session session, String ssoTicket);
}
