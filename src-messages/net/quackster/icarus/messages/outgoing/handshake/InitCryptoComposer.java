package net.quackster.icarus.messages.outgoing.handshake;

import net.quackster.icarus.game.user.SessionEncryption;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

public class InitCryptoComposer extends Response {

	public InitCryptoComposer (SessionEncryption sessionEncryption) {
		
		this.init(Outgoing.InitCryptoMessageComposer);
		this.appendString(sessionEncryption.getRSA().sign(sessionEncryption.getDiffieHellman().getPrime().toString()));
		this.appendString(sessionEncryption.getRSA().sign(sessionEncryption.getDiffieHellman().getGenerator().toString()));
		//this.appendBoolean(false);
	}

}
