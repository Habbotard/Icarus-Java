package net.quackster.icarus.network.handlers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import net.quackster.icarus.messages.Response;
import net.quackster.icarus.network.io.ProtocolEncoder;

public class NetworkEncoder implements ProtocolEncoder {

	@Override
	public ByteBuffer encode(Object message) throws IOException {

		if (message instanceof String) {
			
			String raw = (String)message;
			return ByteBuffer.wrap(raw.getBytes(Charset.forName("UTF-8")));
		}

		if (message instanceof Response) {

			Response msg = (Response)message;
			return msg.getBuffer();
		}


		return null;
	}

}
