package net.quackster.icarus.game.user;

import java.util.List;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.dao.RoomDao;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.user.client.SessionConnection;
import net.quackster.icarus.game.user.client.SessionEncryption;
import net.quackster.icarus.game.user.client.SessionRoom;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class Session {

	private Channel channel;
	private SessionEncryption sessionEncryption;
	private String machineId;
	private CharacterDetails details;
	private SessionConnection connection;
	private SessionRoom roomUser;

	public Session(Channel channel) {

		this.channel = channel;
		this.details = new CharacterDetails();
		this.sessionEncryption = new SessionEncryption();
		this.connection = new SessionConnection(this);
		this.roomUser = new SessionRoom();
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

		if (this.details.isAuthenticated()) {

			List<Room> rooms = RoomDao.getPlayerRooms(this.details.getId());

			if (rooms.size() > 0) {
				for (Room room : rooms) {
					room.dispose();
				}
			}	
		}

		this.channel = null;
		this.details = null;

		this.sessionEncryption.dispose();
		this.sessionEncryption = null;

		this.connection.dispose();
		this.connection = null;
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

	/**
	 * @return the roomUser
	 */
	public SessionRoom getRoomUser() {
		return roomUser;
	}

	/**
	 * @param roomUser the roomUser to set
	 */
	public void setRoomUser(SessionRoom roomUser) {
		this.roomUser = roomUser;
	}
}
