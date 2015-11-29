package net.quackster.icarus.game.user;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.encryption.DiffieHellman;
import net.quackster.icarus.encryption.RC4;
import net.quackster.icarus.encryption.RSA;
import net.quackster.netty.readers.Response;

public class Session {

	private Channel channel;
	private DiffieHellman diffieHellman;
	private RSA RSA;
	private RC4 RC4;
	
	public Session(Channel channel) {
		this.channel = channel;
		this.RSA = new RSA();
		this.diffieHellman = new DiffieHellman();
	}
	
	public void send(Response response) {
		if (response != null) {
			this.channel.write(response);
		} else {
			throw new NullPointerException("Response is null");
		}
	}
	
	public boolean isEncrypted () {
		return this.RC4 != null;
	}
	
	public void setRC4(RC4 rC4) {
		this.RC4 = rC4;
	}
	
	public RC4 getRC4() {
		return RC4;
	}

	public DiffieHellman getDiffieHellman() {
		return diffieHellman;
	}

	public RSA getRSA() {
		return RSA;
	}

}
