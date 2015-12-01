package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class VersionCheckMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		String version = request.readString();
		
		if (!version.equals(Icarus.getRevision())) {
			session.disconnect(); // bad version, kill connection
		}
	}

}
