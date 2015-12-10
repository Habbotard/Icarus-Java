package net.quackster.icarus.game.user;

import java.util.List;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.RoomUser;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class Session {

	private Channel channel;
	private SessionEncryption sessionEncryption;
	private String machineId;
	private CharacterDetails details;
	private SessionConnection connection;
	private RoomUser roomUser;

	public Session(Channel channel) {

		this.channel = channel;
		this.details = new CharacterDetails();
		this.sessionEncryption = new SessionEncryption();
		this.connection = new SessionConnection(this);
		this.roomUser = new RoomUser(this);
	}

	public void invoke(short header, Request message) {
		Icarus.getServer().getMessageHandler().getMessages().get(header).handle(this, message);
	}

	public void checkForDuplicates() {
		
		System.out.println("Sessions: " + Icarus.getServer().getSessionManager().getSessions().size());
		
		for (Session player : Icarus.getServer().getSessionManager().getSessions().values()) {
			if (player.getDetails().getId() == this.getDetails().getId()) {
				if (player.getChannel().getId() != this.getChannel().getId()) { // user tries to login twice
					this.close(); // fuck off
				}
			}
		}
	}

	public void send(Response response) {

		if (response != null) {
			this.channel.write(response);
		} else {
			throw new NullPointerException("Response is null");
		}
	}

	public void close() {
		this.channel.close();
	}

	public void dispose() {

		try {

			if (this.details.isAuthenticated()) {

				if (this.roomUser.inRoom()) {
					this.roomUser.getRoom().leaveRoom(this, false);
				}

				List<Room> rooms = Icarus.getDao().getRoom().getPlayerRooms(this.details);

				if (rooms.size() > 0) {
					for (Room room : rooms) {
						room.dispose();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.roomUser.dispose();
		this.roomUser = null;

		this.details.dispose();
		this.details = null;

		this.sessionEncryption.dispose();
		this.sessionEncryption = null;

		this.connection.dispose();
		this.connection = null;

		//this.channel = null;
		this.machineId = null;
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

	public RoomUser getRoomUser() {
		return roomUser;
	}

	public void setRoomUser(RoomUser roomUser) {
		this.roomUser = roomUser;
	}

}
