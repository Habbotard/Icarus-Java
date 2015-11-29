package net.quackster.messages.incoming.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.messages.Message;
import net.quackster.messages.outgoing.handshake.InitCryptoComposer;
import net.quackster.netty.readers.Request;

public class InitCryptoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
		
		session.send(new InitCryptoComposer(session.getRSA(), session.getDiffieHellman()));

	}
}
