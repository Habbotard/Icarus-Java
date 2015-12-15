package net.quackster.icarus.dao;

import java.util.List;

import net.quackster.icarus.game.messenger.MessengerUser;

public interface IMessengerDao {

	public List<MessengerUser> getFriends(int userId);
	public List<Integer> search(String query);
	public List<MessengerUser> getRequests(int userId);
	public boolean newRequest(int fromId, int toId);
	public boolean removeRequest(int fromId, int toId);
	public boolean newFriend(int sender, int receiver);
}
