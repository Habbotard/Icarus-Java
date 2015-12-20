/*
 * Copyright (c) 2012 Quackster <alex.daniel.97@gmail>. 
 * 
 * This file is part of Sierra.
 * 
 * Sierra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Sierra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Sierra.  If not, see <http ://www.gnu.org/licenses/>.
 */

package org.alexdev.icarus.netty.connections;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageHandler;
import org.alexdev.icarus.netty.codec.NetworkDecoder;
import org.alexdev.icarus.netty.codec.NetworkEncoder;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;

public class Connection {
	
	private int port;
	
	private NioServerSocketChannelFactory factory;
	private ExecutionHandler execute;
	private ServerBootstrap bootstrap;
	private SessionManager Clients;
	private String host;
	private MessageHandler messages;

	public Connection(String host, int port) {

		this.factory = new NioServerSocketChannelFactory (
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()
		);
		
		this.Clients = new SessionManager();
		this.messages = new MessageHandler();
		this.bootstrap = new ServerBootstrap(this.factory);
		
		this.host = host;
		this.port = port;
	}

	public void configureNetty() {
		
		/*this.execute = new ExecutionHandler(
				new OrderedMemoryAwareThreadPoolExecutor(200, 1048576, 1073741824, 100, 
						TimeUnit.MILLISECONDS, 
						Executors.defaultThreadFactory())
		);*/
		
		ChannelPipeline pipeline = this.bootstrap.getPipeline();

		pipeline.addLast("encoder", new NetworkEncoder());
		pipeline.addLast("decoder", new NetworkDecoder());
		pipeline.addLast("handler", new ConnectionHandler());
		//pipeline.addLast("pipelineExecutor", this.execute);
	}

	public boolean listenSocket() {
		try {
			this.bootstrap.bind(new InetSocketAddress(this.host, this.port));
		} catch (ChannelException ex) {
			Log.exception(ex);
			return false;
		}

		return true;
	}
	
	public MessageHandler getMessageHandler() {
		return this.messages;
	}

	public SessionManager getSessionManager() {
		return this.Clients;
	}
	
	public Executor getExecutor() {
		return this.execute.getExecutor();
	}
}
