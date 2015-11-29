package net.quackster.messages.outgoing.handshake;

import net.quackster.icarus.encryption.DiffieHellman;
import net.quackster.icarus.encryption.RSA;
import net.quackster.messages.headers.Outgoing;
import net.quackster.netty.readers.Response;

public class InitCryptoComposer extends Response {

	public InitCryptoComposer(RSA rsa, DiffieHellman diffieHellman) {
		
		this.init(Outgoing.InitCryptoMessageComposer);
		this.appendString(rsa.sign(diffieHellman.getPrime().toString()));
		this.appendString(rsa.sign(diffieHellman.getGenerator().toString()));
	}
}
