package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.handshake.InitCryptoComposer;
import net.quackster.icarus.netty.readers.Request;

public class InitCryptoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		session.send(new InitCryptoComposer(session.getSessionEncryption()));

	}
}
