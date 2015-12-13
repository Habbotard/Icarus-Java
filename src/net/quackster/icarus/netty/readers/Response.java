package net.quackster.icarus.netty.readers;

import java.io.IOException;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

import net.quackster.icarus.log.Log;

public class Response
{
	private int id;
	private boolean finalised;
	private ChannelBufferOutputStream bodystream;
	private ChannelBuffer body;

	public Response() {

	}

	public Response(int id) {
		this.init(id);
	}

	public Response init(int id) {
		
		this.id = id;
		this.finalised = false;
		this.body = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(body);

		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			Log.exception(e);
		}

		return this;
	}

	public void appendString(Object obj) {

		if (obj == null) {
			obj = "";
		}
		
		try {
			bodystream.writeUTF(obj.toString());
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendInt32(Integer obj) {
		try {
			bodystream.writeInt(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendInt32(Boolean obj) {
		try {
			bodystream.writeInt(obj ? 1 : 0);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendShort(int obj) {
		try {
			bodystream.writeShort((short)obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendBoolean(Boolean obj) {
		try {
			bodystream.writeBoolean(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendBody(ISerialize obj) {
		try {
			obj.serialise(this);
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public void AppendResponse(Response obj) {
		try {
			this.bodystream.write(obj.body.array());
		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public String getBodyString() {
		
		String str = new String(this.get().toString(Charset.defaultCharset()));
		
		for (int i = 0; i < 14; i++) { 
			str = str.replace(Character.toString((char)i), "[" + i + "]");
		}

		return str;
	}
	
	public ChannelBuffer get() {

		if (!this.finalised) {
			this.body.setInt(0, this.body.writerIndex() - 4);
			this.finalised = true;
		}
		
		return this.body;
	}

	public int getHeader() {
		return id;
	}

}
