package net.quackster.netty.connections;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.netty.readers.Request;

public class ConnectionHandler extends SimpleChannelHandler
{
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.println("Connection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
		Icarus.getServer().getSessionManager().addSession(ctx.getChannel());

	} 

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
		System.out.println("Disconnection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
		Icarus.getServer().getSessionManager().removeSession(ctx.getChannel());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		
		try {
			
			Session session = (Session) ctx.getChannel().getAttachment();
			Request request = (Request) e.getMessage();
			
			System.out.println("Received: " + request.getMessageId() + " " + request.getMessageBody());
			
			if (session != null){
				Icarus.getServer().getMessageHandler().handleRequest(session, request);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		ctx.getChannel().close();
	}

}
