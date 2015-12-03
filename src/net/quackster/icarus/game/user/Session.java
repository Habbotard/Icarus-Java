package net.quackster.icarus.game.user;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.netty.readers.Response;

public class Session {

	private Channel channel;
	private SessionEncryption sessionEncryption;
	private String machineId;
	private CharacterDetails details;
	
	public Session(Channel channel) {
		
		this.channel = channel;
		this.sessionEncryption = new SessionEncryption();
	}
	
	public void send(Response response) {
		
		if (response != null) {
			this.channel.write(response);
		} else {
			throw new NullPointerException("Response is null");
		}
	}
	
	public void disconnect() {
		
		this.channel.close();
		this.channel = null;
		this.sessionEncryption = null;
	}

	public SessionEncryption getSessionEncryption() {
		return sessionEncryption;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	public String getMachineId() {
		return machineId;
	}

	public CharacterDetails getDetails() {
		return details;
	}
}
