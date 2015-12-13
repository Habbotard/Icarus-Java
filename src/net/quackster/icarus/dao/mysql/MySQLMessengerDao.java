package net.quackster.icarus.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import net.quackster.icarus.dao.IMessengerDao;
import net.quackster.icarus.game.messenger.MessengerFriend;

public class MySQLMessengerDao implements IMessengerDao {

	private MySQLDao dao;

	public MySQLMessengerDao(MySQLDao dao) {
		this.dao = dao;
	}

	@Override
	public List<MessengerFriend> getFriends(int userId) {
		
		List<MessengerFriend> friends = new ArrayList<MessengerFriend>();
		
		friends.add(new MessengerFriend(2));
		
		return friends;
	}

	public MySQLDao getDao() {
		return dao;
	}
}
