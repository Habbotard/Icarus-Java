package net.quackster.netty.messages.handshake;

import net.quackster.icarus.game.user.Session;
import net.quackster.netty.messages.Message;
import net.quackster.netty.messages.headers.Outgoing;
import net.quackster.netty.readers.Request;
import net.quackster.netty.readers.Response;

public class InitCryptoMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		Response message = new Response(Outgoing.InitCryptoMessageComposer);
		message.appendString(session.getRSA().sign(session.getDiffieHellman().prime.toString()));
		message.appendString(session.getRSA().sign(session.getDiffieHellman().generator.toString()));
		session.send(message);

	}

}
