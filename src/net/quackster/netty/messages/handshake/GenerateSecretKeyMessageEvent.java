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

		// RSA sign received public key
		String publicKey = request.readString();
		String encryptedPublicKey = session.getRSA().sign(session.getDiffieHellman().publicKey.toString());
		
		Response message = new Response(Outgoing.SecretKeyMessageComposer);
		message.appendString(encryptedPublicKey);
		message.appendBoolean(false);
		session.send(message);
		
		// calculate key for RC4 decryption
		String plaintextKey = session.getRSA().decrypt(publicKey).replace(Character.toString((char) 0), "");
		session.getDiffieHellman().generateSharedKey(plaintextKey);
		
		// init encryption
		byte[] sharedKey = session.getDiffieHellman().sharedKey.toByteArray();
		session.setRC4(new RC4(sharedKey));

	}

}
