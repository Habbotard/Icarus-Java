package net.quackster.icarus.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.quackster.icarus.dao.IPlayerDao;
import net.quackster.icarus.dao.util.IProcessStorage;
import net.quackster.icarus.game.user.CharacterDetails;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLPlayerDao implements IPlayerDao, IProcessStorage<CharacterDetails, ResultSet> {

	private MySQLDao dao;

	public MySQLPlayerDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public CharacterDetails getDetails(int userId) {
		
		try {
			
			PreparedStatement statement = dao.getStorage().prepare("SELECT * FROM users WHERE id = ? LIMIT 1");
			statement.setInt(1, userId);

			ResultSet row = statement.executeQuery();
			
			CharacterDetails details = new CharacterDetails();
			
			if (!row.next()) {
				return null;
			}

			this.fill(details, row);
			
			Storage.releaseObject(row);
			
			return details;
			
		} catch (SQLException e) {
			Log.exception(e);
		}
		
		return null;
	}
	
	@Override
	public boolean login(Session session, String ssoTicket) {
		
		try {
			
			PreparedStatement statement = dao.getStorage().prepare("SELECT * FROM users WHERE sso_ticket = ? LIMIT 1");
			statement.setString(1, ssoTicket);

			ResultSet row = statement.executeQuery();
			
			if (!row.next()) {
				return false;
			}
			
			this.fill(session.getDetails(), row);
			
			Storage.releaseObject(row);
			
			return true;
			
		} catch (SQLException e) {
			Log.exception(e);
		}
		
		return false;
	}

	@Override
	public CharacterDetails fill(CharacterDetails instance, ResultSet row) throws SQLException {
		
		instance.fill(row.getInt("id"), row.getString("username"), row.getString("motto"),  row.getString("figure"));
		return instance;
	}
	
}
