package net.quackster.icarus.messages.incoming.handshake;

import net.quackster.icarus.encryption.RC4;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.outgoing.handshake.GenerateSecretKeyComposer;
import net.quackster.icarus.netty.readers.Request;

public class GenerateSecretKeyMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		String publicKey = request.readString();
		String encryptedPublicKey = session.getSessionEncryption().getRSA().sign(session.getSessionEncryption().getDiffieHellman().getPublicKey().toString());

		String plaintextKey = session.getSessionEncryption().getRSA().decrypt(publicKey);
		plaintextKey = plaintextKey.replace(Character.toString((char) 0), "");
		
		session.getSessionEncryption().getDiffieHellman().generateSharedKey(plaintextKey);
		byte[] sharedKey = session.getSessionEncryption().getDiffieHellman().getSharedKey().toByteArray();
		
		session.getSessionEncryption().setRC4(new RC4(sharedKey));
		session.getSessionEncryption().setEncrypted(true);
		
		session.send(new GenerateSecretKeyComposer(encryptedPublicKey));

	}

}
