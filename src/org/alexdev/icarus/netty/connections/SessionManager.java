package org.alexdev.icarus.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.user.Session;
import org.jboss.netty.channel.Channel;

public class SessionManager
{
	private ConcurrentMap<Integer, Session> sessions;

	public SessionManager() {
		sessions = new ConcurrentHashMap<Integer, Session>();
	}

	public boolean hasSession(Channel channel) {
		return sessions.containsKey(channel.getId());
	}
	
	public boolean checkForDuplicates(Session session) {
		
		if (session.getDetails() == null || session.getDetails() == null) {
			return false;
		}
		
		for (Session player : Icarus.getServer().getSessionManager().getSessions().values()) {
			
			if (player.getDetails() == null || player.getChannel() == null) {
				continue;
			}
			
			if (player.getDetails().getId() == session.getDetails().getId()) {
				if (player.getChannel().getId() != session.getChannel().getId()) { // user tries to login twice
					return true;
				}
			}
		}
		return false;
	}


	public boolean addSession(Channel channel) {
		
		Session session = new Session(channel);
		channel.setAttachment(session);
		//		this.sessions.put(channel.getId(), session);
		return sessions.putIfAbsent(channel.getId(), session) == null;
	}

	public void removeSession(Channel channel)
	{
		try {
			//System.out.println("Before: " + sessions.size());
			sessions.remove(channel.getId());
			//System.out.println("After: " + sessions.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Session findById(int userId) {
		
		try {
			return this.sessions.values().stream().filter(s -> s.getDetails().getId() == userId).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Session findByName(String name) {
		
		try {
			return this.sessions.values().stream().filter(s -> s.getDetails().getUsername().equals(name)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public ConcurrentMap<Integer, Session> getSessions() {
		return sessions;
	}
}
