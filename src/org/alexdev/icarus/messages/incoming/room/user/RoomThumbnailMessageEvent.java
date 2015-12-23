package org.alexdev.icarus.messages.incoming.room.user;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.netty.readers.Request;

public class RoomThumbnailMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		int count = request.readInt();

		byte[] bytes = request.readBytes(count);
		byte[] buffer = new byte[bytes.length * 3];

		try {

			Inflater inflater = new Inflater();
			inflater.setInput(bytes);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			while (!inflater.finished()) {
				int amount = inflater.inflate(buffer);
				outputStream.write(buffer, 0, amount);
			}

			//outputStream.close();
			//byte[] output = outputStream.toByteArray();

			inflater.end();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
