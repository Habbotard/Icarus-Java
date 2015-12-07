package net.quackster.icarus.netty.connections;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import net.quackster.icarus.Icarus;
import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.log.Log;
import net.quackster.icarus.netty.readers.Request;

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
		
		Session session = (Session) ctx.getChannel().getAttachment();
		session.dispose();
		
		Icarus.getServer().getSessionManager().removeSession(ctx.getChannel());
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
