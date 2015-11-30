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
	private ChannelBufferOutputStream bodystream;
	private ChannelBuffer bodeh;
	
	public Response() {
		
	}
	
	public Response(int id) 
	{
		this.id = id;
		this.bodeh = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(bodeh);

		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			Log.exception(e);
		}
	}

	public Response init(int id)
	{
		this.id = id;
		this.bodeh = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(bodeh);

		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			Log.exception(e);
		}
		return this;
	}
	
	public void appendString(Object obj)
	{
		try {
			bodystream.writeUTF(obj.toString());
		} catch (IOException e) {
			Log.exception(e);
		}
	}
	
	public void appendInt32(Integer obj)
	{
		try {
			bodystream.writeInt(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}
	
	public void appendInt32(Boolean obj)
	{
		try {
			bodystream.writeInt(obj ? 1 : 0);
		} catch (IOException e) {
			Log.exception(e);
		}
	}
	
	public void appendShort(int obj)
	{
		try {
			bodystream.writeShort((short)obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public void appendBoolean(Boolean obj)
	{
		try {
			bodystream.writeBoolean(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}
	
	public void appendBody(ISerialize obj)
	{
		try {
			obj.serialize(this);
		} catch (Exception e) {
			Log.exception(e);
		}
	}
	
	public void AppendResponse(Response obj)
	{
		try {
			this.bodystream.write(obj.bodeh.array());
		} catch (Exception e) {
			Log.exception(e);
		}
	}
	
	public String getBodyString()
	{
		ChannelBuffer body = bodeh.duplicate();
		body.setInt(0, body.writerIndex() - 4);
		
		String str = new String(body.toString(Charset.defaultCharset()));

		String consoleText = str;

		for (int i = 0; i < 14; i++) { 
			consoleText = consoleText.replace(Character.toString((char)i), "[" + i + "]");
		}

		return consoleText;
	}
	
	public int getHeader() {
		return id;
	}
	
	public ChannelBuffer get() {
		ChannelBuffer body = bodeh.duplicate();
		body.setInt(0, body.writerIndex() - 4);
		return body;
	}
}
