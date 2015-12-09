package net.quackster.icarus.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;

public class SessionManager
{
	private ConcurrentMap<Integer, Session> sessions;

	public SessionManager() {
		sessions = new ConcurrentHashMap<Integer, Session>();
	}

	public boolean hasSession(Channel channel) {
		return sessions.containsKey(channel.getId());
	}

	public boolean addSession(Channel channel) {
		
		Session session = new Session(channel);
		channel.setAttachment(session);
		return sessions.putIfAbsent(channel.getId(), session) == null;
	}

	public void removeSession(Channel channel)
	{
		try {
			sessions.remove(channel.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Session findById (int userId) {
		
		try {
			return this.sessions.values().stream().filter(s -> s.getDetails().getId() == userId).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ConcurrentMap<Integer, Session> getSessions() {
		return sessions;
	}
}
