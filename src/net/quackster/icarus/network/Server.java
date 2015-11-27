/*
  (c) 2004, Nuno Santos, nfsantos@sapo.pt
  relased under terms of the GNU public license 
  http://www.gnu.org/licenses/licenses.html#TOCGPL
 */
package net.quackster.icarus.network;


import java.io.IOException;
import java.nio.channels.SocketChannel;

import net.quackster.icarus.network.handlers.NetworkDecoder;
import net.quackster.icarus.network.handlers.NetworkEncoder;
import net.quackster.icarus.network.handlers.listeners.AcceptorListener;
import net.quackster.icarus.network.handlers.listeners.NetworkListener;
import net.quackster.icarus.network.io.*;


public class Server implements AcceptorListener {
	
	private final SelectorThread st;
	private Acceptor acceptor;  

	public Server(int listenPort) throws Exception {
		st = new SelectorThread();
		acceptor = new Acceptor(listenPort, st, this);
		acceptor.openServerSocket();
		System.out.println("Listening on port: " + listenPort);
	} 

	public void socketConnected(Acceptor acceptor, SocketChannel sc) {    
		
		System.out.println("["+ acceptor + "] Socket connected: " + sc.socket().getInetAddress());
		
		try {

			sc.socket().setReceiveBufferSize(2*1024);
			sc.socket().setSendBufferSize(2*1024);

			PacketChannel pc = new PacketChannel(sc, st, new NetworkDecoder(), new NetworkEncoder(), new NetworkListener());
			pc.resumeReading();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void socketError(Acceptor acceptor, Exception ex) {
		System.out.println("["+ acceptor + "] Error: " + ex.getMessage());
	}
}
