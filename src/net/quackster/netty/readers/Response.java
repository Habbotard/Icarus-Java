package net.quackster.netty.readers;

import java.io.IOException;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class Response
{
	private int Id;
	private ChannelBufferOutputStream bodystream;
	private ChannelBuffer bodeh;
	
	public Response() {
		
	}
	
	public Response(int id) 
	{
		this.Id = id;
		this.bodeh = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(bodeh);

		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Response init(int id)
	{
		this.Id = id;
		this.bodeh = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(bodeh);

		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void appendString(Object obj)
	{
		try {
			bodystream.writeUTF(obj.toString());
		} catch (IOException e) {
		}
	}
	
	public void appendInt32(Integer obj)
	{
		try {
			bodystream.writeInt(obj);
		} catch (IOException e) {
		}
	}
	
	public void appendInt32(Boolean obj)
	{
		try {
			bodystream.writeInt(obj ? 1 : 0);
		} catch (IOException e) {
		}
	}
	
	public void appendShort(int obj)
	{
		try {
			bodystream.writeShort((short)obj);
		} catch (IOException e) {
		}
	}

	public void appendBoolean(Boolean obj)
	{
		try {
			bodystream.writeBoolean(obj);
		} catch (IOException e) {
		}
	}
	
	public void appendBody(ISerialize obj)
	{
		try {
			obj.serialize(this);
		} catch (Exception e) {
		}
	}
	
	public void AppendResponse(Response obj)
	{
		try {
			this.bodystream.write(obj.bodeh.array());
		} catch (Exception e) {
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

		return Id;
	}
	
	public ChannelBuffer get() {
		ChannelBuffer body = bodeh.duplicate();
		body.setInt(0, body.writerIndex() - 4);
		return body;
	}
}
