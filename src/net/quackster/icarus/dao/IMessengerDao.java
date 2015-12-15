package net.quackster.icarus.dao;

import java.util.List;

import net.quackster.icarus.game.messenger.MessengerFriend;

public interface IMessengerDao {

	public List<MessengerFriend> getFriends(int userId);
	public List<Integer> search(String query);
}
