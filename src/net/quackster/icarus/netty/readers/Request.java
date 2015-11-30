package net.quackster.icarus.netty.readers;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class Request
{
	private int header;
	public ChannelBuffer buffer;

	public Request(int messageId, ChannelBuffer buffer) {
		this.header = messageId;
		this.buffer = (buffer == null || buffer.readableBytes() == 0) ? ChannelBuffers.EMPTY_BUFFER : buffer;
	}

	public Request clone() {
		return new Request(this.header, this.buffer.duplicate());
	}

	public int readShort() {
		return buffer.readShort();
	}

	public Integer readInt() {
		try {
			return buffer.readInt();
		} catch (Exception e) {
			return 0;
		}
	}


	public boolean readBoolean()  {
		try {
			return buffer.readByte() == 1;
		}
		catch (Exception e)	{
			return false;
		}
	}

	public String readString() {
		
		try {
			int Length = this.readShort();
			byte[] Data = this.buffer.readBytes(Length).array();

			return new String(Data);
		} catch (Exception e) {
			return "invalid:";
		}
	}

	public String getMessageBody() {
		
		String consoleText = new String(buffer.toString(Charset.defaultCharset()));

		for (int i = 0; i < 13; i++) { 
			consoleText = consoleText.replace(Character.toString((char)i), "[" + i + "]");
		}

		return consoleText;
	}
	
	public ChannelBuffer getBuffer() {
		return buffer;
	}

	public int getMessageId() {
		return header;
	}
}
