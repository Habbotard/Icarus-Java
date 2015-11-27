package net.quackster.icarus.network.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ProtocolEncoder {
	public abstract ByteBuffer encode(Object object) throws IOException;
}
