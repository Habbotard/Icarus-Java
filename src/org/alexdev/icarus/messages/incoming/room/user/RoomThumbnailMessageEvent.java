package org.alexdev.icarus.messages.incoming.room.user;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.Message;
import org.alexdev.icarus.netty.readers.Request;

public class RoomThumbnailMessageEvent implements Message {

	@Override
	public void handle(Session session, Request request) {

		int count = request.readInt();
		byte[] bytes = request.readBytes(count);

		try {
			
			//Inflater data = new Inflater();
			//data.inflate(bytes, 0, bytes.length);

			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("file"), "utf-8")); {
				writer.write(new String(bytes));
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
