package net.quackster.icarus.game.user;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.client.SessionConnection;
import net.quackster.icarus.game.user.client.SessionEncryption;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class Session {

	private Channel channel;
	private SessionEncryption sessionEncryption;
	private String machineId;
	private CharacterDetails details;
	private SessionConnection connection;

	public Session(Channel channel) {

		this.channel = channel;
		this.details = new CharacterDetails();
		this.sessionEncryption = new SessionEncryption();
		this.connection = new SessionConnection();
	}
	
	public void invoke(short header, Request message) {
		Icarus.getServer().getMessageHandler().getMessages().get(header).handle(this, message);
	}

	public void send(Response response) {

		if (response != null) {
			this.channel.write(response);
		} else {
			throw new NullPointerException("Response is null");
		}
	}

	public void close() {
		this.dispose(true);
	}
	
	public void dispose(boolean disconnect) {

		if (disconnect) {
			this.channel.close();
		}
		
		this.channel = null;
		this.sessionEncryption = null;
		this.details = null;
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

	public SessionConnection getConnection() {
		return connection;
	}

	public CharacterDetails getDetails() {
		return details;
	}
}
