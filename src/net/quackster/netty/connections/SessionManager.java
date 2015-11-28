package net.quackster.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.game.user.Session;

public class SessionManager
{
	private ConcurrentMap<Integer, Session> Sessions;

	public SessionManager() {
		Sessions = new ConcurrentHashMap<Integer, Session>();
	}

	public boolean hasSession(Channel channel) {
		return Sessions.containsKey(channel.getId());
	}

	public boolean addSession(Channel channel) {
		
		Session session = new Session(channel);
		channel.setAttachment(session);
		return Sessions.putIfAbsent(channel.getId(), session) == null;
	}

	public void removeSession(Channel channel)
	{
		try {
			Sessions.remove(channel.getId());
		} catch (Exception e) {
		}
	}
	
	public ConcurrentMap<Integer, Session> getSessions() {
		return Sessions;
	}
}
