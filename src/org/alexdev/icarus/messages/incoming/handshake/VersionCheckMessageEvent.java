package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class VersionCheckMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {
		
		String version = request.readString();
		
		if (!version.equals(Icarus.getRevision())) {
			session.close(); // bad version, kill connection
			return;
		}
	}

}
