package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;

public class UniqueIDMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		request.readString();
		String machineId = request.readString();
		session.setMachineId(machineId);
	}

}
