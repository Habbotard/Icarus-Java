/*
  (c) 2004, Nuno Santos, nfsantos@sapo.pt
  relased under terms of the GNU public license 
  http://www.gnu.org/licenses/licenses.html#TOCGPL
 */
package net.quackster.icarus.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import net.quackster.icarus.network.handlers.listeners.ConnectorListener;
import net.quackster.icarus.network.io.CallbackErrorHandler;
import net.quackster.icarus.network.io.ConnectorSelectorHandler;
import net.quackster.icarus.network.io.SelectorThread;

/**
 * Manages a non-blocking connection attempt to a remote host. 
 * 
 * @author Nuno Santos
 */
final public class Connector implements ConnectorSelectorHandler {

	private SocketChannel sc;  
	private final InetSocketAddress remoteAddress;
	private final SelectorThread selectorThread;
	private final ConnectorListener listener;

	/**
	 * Creates a new instance. The connection is not attempted here.
	 * Use connect() to start the attempt.
	 * 
	 * @param remoteAddress The remote endpoint where to connect.
	 * @param listener The object that will receive the callbacks from
	 * this Connector.
	 * @param selector The selector to be used.
	 * @throws IOException
	 */
	public Connector(SelectorThread selector, InetSocketAddress remoteAddress, ConnectorListener listener) {     
		this.selectorThread = selector;
		this.remoteAddress = remoteAddress;
		this.listener = listener;
	}

	/**
	 * Starts a non-blocking connection attempt.
	 *   
	 * @throws IOException
	 */
	public void connect() throws IOException {
		
		sc = SocketChannel.open();  
		sc.configureBlocking(false);
		sc.connect(remoteAddress);

		selectorThread.registerChannelLater(sc, SelectionKey.OP_CONNECT, this,
				new CallbackErrorHandler() {
					public void handleError(Exception ex) {    
						listener.connectionFailed(Connector.this, ex);
					}
				});
	}

	/**
	 * Called by the selector thread when the connection is 
	 * ready to be completed.
	 */
	public void handleConnect() {
		try {
			
			if (!sc.finishConnect()) {
				listener.connectionFailed(this, null);
				return;
			}
			
			listener.connectionEstablished(this, sc);
		} catch (IOException ex) {      
			listener.connectionFailed(this, ex);
		}
	}

	public String toString() {   
		return "Remote endpoint: " + 
				remoteAddress.getAddress().getHostAddress() + ":" + remoteAddress.getPort();
	}
}