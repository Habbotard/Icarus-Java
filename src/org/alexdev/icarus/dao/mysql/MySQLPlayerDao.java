package org.alexdev.icarus.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.dao.IPlayerDao;
import org.alexdev.icarus.dao.util.IProcessStorage;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.user.CharacterDetails;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.mysql.Storage;

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

			CharacterDetails details = new CharacterDetails(null);

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
	public int getId(String username) {


		try {

			PreparedStatement statement = dao.getStorage().prepare("SELECT * FROM users WHERE username = ? LIMIT 1", true); {
				statement.setString(1, username);
			}
			
			ResultSet row = statement.executeQuery();
			row.next();
			
			int id = row.getInt("id");
			
			Storage.releaseObject(row);
			
			return id;

		} catch (SQLException e) {
			Log.exception(e);
		}

		return -1;	
	}

	@Override
	public CharacterDetails fill(CharacterDetails instance, ResultSet row) throws SQLException {

		instance.fill(row.getInt("id"), row.getString("username"), row.getString("motto"),  row.getString("figure"), row.getInt("rank"), row.getInt("credits"));
		return instance;
	}
}
