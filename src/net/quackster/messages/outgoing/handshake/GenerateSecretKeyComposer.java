package net.quackster.messages.outgoing.handshake;

import net.quackster.messages.headers.Outgoing;
import net.quackster.netty.readers.Response;

public class GenerateSecretKeyComposer extends Response {
	
	public GenerateSecretKeyComposer(String encryptedPublicKey) {
		
		this.init(Outgoing.SecretKeyMessageComposer);	
		this.appendString(encryptedPublicKey);
		this.appendBoolean(false);
	}
}
