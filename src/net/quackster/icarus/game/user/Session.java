package net.quackster.icarus.game.user;

import java.util.List;

import org.jboss.netty.channel.Channel;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.entity.EntityType;
import net.quackster.icarus.game.entity.IEntity;
import net.quackster.icarus.game.messenger.Messenger;
import net.quackster.icarus.game.room.Room;
import net.quackster.icarus.game.room.player.RoomUser;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class Session implements IEntity {

	private Channel channel;
	private String machineId;
	private CharacterDetails details;
	private SessionConnection connection;
	private RoomUser roomUser;
	private Messenger messenger;

	public Session(Channel channel) {

		this.channel = channel;
		this.details = new CharacterDetails();
		this.connection = new SessionConnection(this);
		this.roomUser = new RoomUser(this);
		this.messenger = new Messenger(this);
	}

	public List<Room> getRooms() {
		return Icarus.getGame().getRoomManager().getPlayerRooms(this.details.getId());
	}
	
	public void invoke(short header, Request message) {
		Icarus.getServer().getMessageHandler().getMessages().get(header).handle(this, message);
	}

	public void send(Response response) {
		
		if (response != null) {
			//Log.println("(" + this.details.getUsername() + ") SENT: " + response.getHeader() + " / " + response.getBodyString());
			this.channel.write(response);
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

				List<Room> rooms = Icarus.getGame().getRoomManager().getPlayerRooms(this.details.getId());

				if (rooms.size() > 0) {
					for (Room room : rooms) {
						// this function won't dispose all rooms if there's still users online 
						// and/or the owner is still online
						room.dispose(); 
					}
				}
			}
		} catch (Exception e) {
			Log.exception(e);
		}
		
		this.messenger.dispose();
		this.messenger = null;

		this.roomUser.dispose();
		this.roomUser = null;

		this.details.dispose();
		this.details = null;
		
		this.connection.dispose();
		this.connection = null;

		this.channel = null;
		this.machineId = null;
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

	public Messenger getMessenger() {
		return messenger;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

}
