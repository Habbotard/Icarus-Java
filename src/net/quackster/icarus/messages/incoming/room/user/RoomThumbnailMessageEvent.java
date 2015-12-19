package net.quackster.icarus.messages.incoming.room.user;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.util.GZIPCompression;

public class RoomThumbnailMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {
	
		int count = request.readInt();
        
		byte[] bytes = request.readBytes(count);
        
        String file = "file1.png";
        try {
			String data = GZIPCompression.decompress(bytes);
			
			GZIPCompression.write(file, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
