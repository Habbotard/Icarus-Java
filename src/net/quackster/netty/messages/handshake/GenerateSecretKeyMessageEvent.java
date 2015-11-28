package net.quackster.netty.messages.handshake;

import net.quackster.icarus.encryption.RC4;
import net.quackster.icarus.game.user.Session;
import net.quackster.netty.messages.Message;
import net.quackster.netty.messages.headers.Outgoing;
import net.quackster.netty.readers.Request;
import net.quackster.netty.readers.Response;

public class GenerateSecretKeyMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		String publicKey = request.readString();
		String plaintextKey = session.getRSA().Decrypt(publicKey).replace(Character.toString((char) 0), "");
		String encP = session.getRSA().Sign(session.getDiffieHellman().PublicKey.toString());
		
		Response message = new Response(Outgoing.SecretKeyMessageComposer);
		message.appendString(encP);
		message.appendBoolean(false);
		session.send(message);
		
		session.getDiffieHellman().GenerateSharedKey(plaintextKey);
		byte[] sharedKey = session.getDiffieHellman().SharedKey.toByteArray();
		session.setRC4(new RC4(sharedKey));

	}

}
