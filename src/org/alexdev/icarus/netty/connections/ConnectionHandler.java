package org.alexdev.icarus.netty.connections;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.netty.readers.Request;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ConnectionHandler extends SimpleChannelHandler {
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {

		if (Icarus.getUtilities().getConfiguration().getBoolean("log-connections")) {
			Log.println("Connection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
		}
		
		Icarus.getServer().getSessionManager().addSession(ctx.getChannel());

	} 

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {

		if (Icarus.getUtilities().getConfiguration().getBoolean("log-connections")) {
			Log.println("Disconnection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
		}
		
		Icarus.getServer().getSessionManager().removeSession(ctx.getChannel());
		
		Session session = (Session) ctx.getChannel().getAttachment();
		session.dispose();
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

		try {

			Session session = (Session) ctx.getChannel().getAttachment();
			Request request = (Request) e.getMessage();

			if (Icarus.getUtilities().getConfiguration().getBoolean("log-packets")) {
				Log.println("Received: " + request.getMessageId() + " " + request.getMessageBody());
			}

			if (session != null){
				Icarus.getServer().getMessageHandler().handleRequest(session, request);
			}

		} catch (Exception ex) {
			Log.exception(ex);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		ctx.getChannel().close();
	}

}
