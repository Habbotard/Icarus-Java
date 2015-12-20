package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class ChatOptionsMessageComposer extends Response {

	public ChatOptionsMessageComposer(Room room) {
		
		this.init(Outgoing.ChatOptionsMessageComposer);
        this.appendInt32(room.getData().getChatType());
        this.appendInt32(room.getData().getChatBalloon());
        this.appendInt32(room.getData().getChatSpeed());
        this.appendInt32(room.getData().getChatMaxDistance());
        this.appendInt32(room.getData().getChatFloodProtection());
	}
}
