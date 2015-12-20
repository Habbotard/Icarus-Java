package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class UniqueIDMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		request.readString();
		String machineId = request.readString();
		session.setMachineId(machineId);
	}

}
