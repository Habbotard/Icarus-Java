/*
  (c) 2004, Nuno Santos, nfsantos@sapo.pt
  relased under terms of the GNU public license 
  http://www.gnu.org/licenses/licenses.html#TOCGPL
 */
package net.quackster.icarus.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;

import net.quackster.icarus.network.handlers.listeners.AcceptorListener;
import net.quackster.icarus.network.io.AcceptSelectorHandler;
import net.quackster.icarus.network.io.CallbackErrorHandler;
import net.quackster.icarus.network.io.SelectorThread;

/**
 * Listens for incoming connections from clients, using a selector
 * to receive connect events. Therefore, instances of this class
 * don't have an associated thread. When a connection is established,
 * it notifies a listener using a callback.
 * 
 * @author Nuno Santos
 */
final public class Acceptor implements AcceptSelectorHandler {
	
	private ServerSocketChannel ssc; 
	private final SelectorThread ioThread;  
	private final int listenPort;  
	private final AcceptorListener listener;

	/**
	 * Creates a new instance. No server socket is created. Use 
	 * openServerSocket() to start listening.
	 * 
	 * @param listenPort The port to open.
	 * @param listener The object that will receive notifications 
	 *  of incoming connections.
	 * @param ioThread The selector thread.
	 * 
	 * @throws IOException
	 */
	public Acceptor(int listenPort, SelectorThread ioThread, AcceptorListener listener)    
	{ 
		this.ioThread = ioThread;
		this.listenPort = listenPort;
		this.listener = listener;
	}

	/**
	 * Starts listening for incoming connections. This method does 
	 * not block waiting for connections. Instead, it registers itself 
	 * with the selector to receive connect events.
	 * 
	 * @throws IOException
	 */
	public void openServerSocket() throws IOException {
		
		ssc = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress(listenPort);
		ssc.socket().bind(isa, 100);

		ioThread.registerChannelLater(ssc, SelectionKey.OP_ACCEPT, this, new CallbackErrorHandler() {
			public void handleError(Exception ex) {    
				listener.socketError(Acceptor.this, ex);
			}
		});
	}

	public String toString() {  
		return "ListenPort: " + listenPort;
	}

	/**
	 * Called by SelectorThread when the underlying server socket is 
	 * ready to accept a connection. This method should not be called
	 * from anywhere else.
	 */
	public void handleAccept() {
		SocketChannel sc = null;
		try {
			
			sc = ssc.accept();
			ioThread.addChannelInterestNow(ssc, SelectionKey.OP_ACCEPT);

		} catch (IOException e) {
			listener.socketError(this, e);
		}
		if (sc != null) {
			// Connection established
			listener.socketConnected(this, sc);
		}
	}

	/**
	 * Closes the socket. Returns only when the socket has been
	 * closed.
	 */
	public void close()  {
		try {
			// Must wait for the socket to be closed.
			ioThread.invokeAndWait(new Runnable() {      
				public void run() {
					if (ssc != null) {
						try {
							ssc.close();
						} catch (IOException e) {
							// Ignore
						}
					}        
				}
			});
		} catch (InterruptedException e) {
			// Ignore
		}
	}  
}