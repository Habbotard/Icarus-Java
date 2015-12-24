package org.alexdev.icarus.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.IMessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.log.Log;

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
				users.add(new MessengerUser(row.getInt("from_id")));
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

			if (!this.dao.getStorage().exists("SELECT * FROM messenger_requests WHERE to_id = '" + toId + "' AND from_id = '" + fromId + "'") && !this.dao.getStorage().exists("SELECT * FROM messenger_requests WHERE from_id = '" + toId + "' AND to_id = '" + fromId + "'")) {

				statement = dao.getStorage().prepare("INSERT INTO messenger_requests (to_id, from_id) VALUES (?, ?)"); {
					statement.setInt(1, toId);
					statement.setInt(2, fromId);
					statement.execute();
				}

				return true;
			}

		} catch (SQLException e) {
			Log.exception(e);
		}

		return false;
	}

	@Override
	public boolean removeRequest(int fromId, int toId) {
		this.dao.getStorage().execute("DELETE FROM messenger_requests WHERE from_id = " + fromId + " AND to_id = " + toId);
		return false;
	}

	@Override
	public boolean removeFriend(int friendId, int userId) {
		this.dao.getStorage().execute("DELETE FROM messenger_friendships WHERE (sender = " + userId + " AND receiver = " + friendId + ") OR (receiver = " + userId + " AND sender = " + friendId + ")");
		return false;
	}

	@Override
	public boolean newFriend(int sender, int receiver) {

		PreparedStatement statement = null;

		try {

			statement = dao.getStorage().prepare("INSERT INTO messenger_friendships (sender, receiver) VALUES (?, ?)"); {
				statement.setInt(1, sender);
				statement.setInt(2, receiver);
				statement.execute();
			}

			return true;

		} catch (SQLException e) {
			Log.exception(e);
		}

		return false;
	}

	public MySQLDao getDao() {
		return dao;
	}
}
