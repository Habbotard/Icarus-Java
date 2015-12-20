package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class GenerateSecretKeyComposer extends Response {
	
	public GenerateSecretKeyComposer(String encryptedPublicKey) {
		
		this.init(Outgoing.SecretKeyMessageComposer);	
		this.appendString(encryptedPublicKey);
		this.appendBoolean(false);
	}
}
