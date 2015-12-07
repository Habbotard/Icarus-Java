package net.quackster.icarus.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;

public class PlayerDao {

	public static boolean login(Session session, String ssoTicket) {
		
		try {
			
			PreparedStatement statement = Icarus.getStorage().prepare("SELECT * FROM users WHERE sso_ticket = ? LIMIT 1");
			statement.setString(1, ssoTicket);

			ResultSet row = statement.executeQuery();
			
			if (!row.next()) {
				return false;
			}
			
			session.getDetails().fill(row);
			return true;
			
		} catch (SQLException e) {
			Log.exception(e);
		}
		
		return false;
	}
	
}
