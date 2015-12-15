package net.quackster.icarus.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.dao.IMessengerDao;
import net.quackster.icarus.game.messenger.MessengerUser;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.mysql.Storage;

public class MySQLMessengerDao implements IMessengerDao {

	private MySQLDao dao;

	public MySQLMessengerDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<MessengerUser> getFriends(int userId) {

		List<MessengerUser> friends = new ArrayList<MessengerUser>();

		ResultSet row = null;

		try {

			row = dao.getStorage().getTable("SELECT * FROM messenger_friendships WHERE (sender = " + userId + ") OR (receiver = " + userId + ")");

			while (row.next()) {

				MessengerUser friend = null;

				if (row.getInt("sender") != userId) {
					friend = new MessengerUser(row.getInt("sender"));
				} else {
					friend = new MessengerUser(row.getInt("receiver"));
				}

				friends.add(friend);
			}

		} catch (SQLException e) {
			Log.exception(e);
		}

		Storage.releaseObject(row);

		return friends;
	}


	@Override
	public List<MessengerUser> getRequests(int userId) {

		List<MessengerUser> users = new ArrayList<MessengerUser>();

		ResultSet row = null;

		try {

			row = dao.getStorage().getTable("SELECT * FROM messenger_requests WHERE to_id = " + userId);

			while (row.next()) {
				users.add(new MessengerUser(row.getInt("to_id")));
			}

		} catch (SQLException e) {
			Log.exception(e);
		}

		Storage.releaseObject(row);

		return users;
	}

	@Override
	public List<Integer> search(String query) {

		List<Integer> users = new ArrayList<Integer>();

		ResultSet row = null;

		try {

			PreparedStatement statement = this.dao.getStorage().prepare("SELECT id FROM users WHERE username LIKE ? LIMIT 30"); {
				statement.setString(1, "%" + query + "%");
			}

			row = statement.executeQuery();

			while (row.next()) {
				users.add(row.getInt("id"));
			}

		} catch (SQLException e) {
			Log.exception(e);
		}

		Storage.releaseObject(row);

		return users;
	}

	@Override
	public boolean newRequest(int fromId, int toId) {

		PreparedStatement statement = null;

		try {

			if (!this.dao.getStorage().exists("SELECT * FROM messenger_requests WHERE to_id = '" + toId + "' AND from_id = '" + fromId + "'")) {

				if (!this.dao.getStorage().exists("SELECT * FROM messenger_requests WHERE from_id = '" + toId + "' AND to_id = '" + fromId + "'")) {

					statement = dao.getStorage().prepare("INSERT INTO messenger_requests (to_id, from_id) VALUES (?, ?)"); {
						statement.setInt(1, toId);
						statement.setInt(2, fromId);
						statement.execute();
					}
					

					return true;
				}
			}

		} catch (SQLException e) {
			Log.exception(e);
		}

		return false;
	}

	public MySQLDao getDao() {
		return dao;
	}
}
