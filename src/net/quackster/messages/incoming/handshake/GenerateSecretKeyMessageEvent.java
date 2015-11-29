package net.quackster.messages.incoming.handshake;

import net.quackster.icarus.encryption.RC4;
import net.quackster.icarus.game.user.Session;
import net.quackster.messages.Message;
import net.quackster.messages.outgoing.handshake.GenerateSecretKeyComposer;
import net.quackster.netty.readers.Request;

public class GenerateSecretKeyMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		String publicKey = request.readString();
		String encryptedPublicKey = session.getRSA().sign(session.getDiffieHellman().getPublicKey().toString());

		String plaintextKey = session.getRSA().decrypt(publicKey);
		plaintextKey = plaintextKey.replace(Character.toString((char) 0), "");
		
		session.getDiffieHellman().generateSharedKey(plaintextKey);
		byte[] sharedKey = session.getDiffieHellman().getSharedKey().toByteArray();
		session.setRC4(new RC4(sharedKey));
		
		session.send(new GenerateSecretKeyComposer(encryptedPublicKey));

	}

}
