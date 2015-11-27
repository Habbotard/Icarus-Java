/*
  (c) 2004, Nuno Santos, nfsantos@sapo.pt
  relased under terms of the GNU public license 
  http://www.gnu.org/licenses/licenses.html#TOCGPL
 */
package net.quackster.icarus.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import net.quackster.icarus.network.handlers.listeners.PacketChannelListener;
import net.quackster.icarus.network.io.ProtocolDecoder;
import net.quackster.icarus.network.io.ProtocolEncoder;
import net.quackster.icarus.network.io.ReadWriteSelectorHandler;
import net.quackster.icarus.network.io.SelectorThread;

/**
 * Uses non-blocking operations to read and write from a socket. Internally,
 * this class uses a selector to receive read and write events from the
 * underlying socket.  
 *  
 * Methods on this class should be called only by the selector's thread 
 * (including the constructor). If necessary, use Selector.invokeLater() 
 * to dispatch a invocation to the selector's thread.
 * 
 * @author Nuno Santos
 */
final public class PacketChannel implements ReadWriteSelectorHandler {
	
	protected final SelectorThread selector;
	private final SocketChannel sc;
	private ByteBuffer inBuffer;
	private ByteBuffer outBuffer = null;
	private final ProtocolDecoder protocolDecoder;
	private ProtocolEncoder protocolEncoder;
	private final PacketChannelListener listener;

	public PacketChannel(SocketChannel socketChannel, SelectorThread selector, ProtocolDecoder protocolDecoder, ProtocolEncoder protocolEncoder, PacketChannelListener listener) throws IOException {
		this.selector = selector;
		this.protocolDecoder = protocolDecoder;   
		this.protocolEncoder = protocolEncoder;
		this.sc = socketChannel;
		this.listener = listener;

		inBuffer = ByteBuffer.allocateDirect(sc.socket().getReceiveBufferSize());
		inBuffer.position(inBuffer.capacity());

		selector.registerChannelNow(sc, 0,  this);
	}

	/**
	 * Activates reading from the socket. This method is non-blocking. 
	 */
	public void resumeReading() throws IOException {
		processInBuffer();
	}

	public void close() {
		try {
			sc.close();
		} catch (IOException e) {
			// Ignore
		}
	}

	/**
	 * Reads from the socket into the internal buffer. This method should 
	 * be called only from the SelectorThread class.
	 */
	public void handleRead() {
		try {      
			
			int readBytes = sc.read(inBuffer);      
			
			if (readBytes == -1) {
				close();
				listener.socketDisconnected(this);
				return;
			}

			if (readBytes == 0) {        
				reactivateReading();
				return;
			}

			inBuffer.flip();
			processInBuffer();
			
		} catch (IOException ex) {
			listener.socketException(this, ex);
			close();
		}
	}

	/**
	 * Processes the internal buffer, converting it into packets if enough
	 * data is available.
	 */
	private void processInBuffer() throws IOException {    
		
		Object packet = protocolDecoder.decode(inBuffer);
		
		if (packet == null) {      
			inBuffer.clear();
			reactivateReading();
		} else {      
			listener.packetArrived(this, packet);
		}
	}

	/**
	 * Disable interest in reading.
	 * @throws IOException 
	 */
	public void disableReading() throws IOException {
		selector.removeChannelInterestNow(sc, SelectionKey.OP_READ);    
	}

	/**
	 * Enables interest in reading.
	 * @throws IOException
	 */
	private void reactivateReading() throws IOException {
		selector.addChannelInterestNow(sc, SelectionKey.OP_READ);
	}

	/**
	 * Sends a packet using non-blocking writes. One packet cannot be sent 
	 * before the previous one has been dispatched. The caller must ensure 
	 * this. 
	 * 
	 * This class keeps a reference to buffer given as argument while sending
	 * it. So it is important not to change this buffer after calling this 
	 * method.  
	 * 
	 * @param packet The packet to be sent. 
	 */
	
	public void sendPacket(Object message) {
		
		try {
			
			ByteBuffer packet = this.protocolEncoder.encode(message);	
			outBuffer = packet;
			handleWrite();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendPacket(ByteBuffer packet) {
		// keeps a reference to the packet. In production code this should copy
		// the contents of the buffer.
		outBuffer = packet;
		handleWrite();
	}

	/**
	 * Activates interest in writing.
	 * 
	 * @throws IOException
	 */
	private void requestWrite() throws IOException {
		selector.addChannelInterestNow(sc, SelectionKey.OP_WRITE);
	}

	/**
	 * Writes to the underlying channel. Non-blocking. This method is called
	 * only from sendPacket() and from the SelectorThread class.
	 */
	public void handleWrite() {    
		try {
			
			if (outBuffer.hasRemaining()) {
				requestWrite();
			} else {
				
				ByteBuffer sentPacket = outBuffer;
				outBuffer = null;
				listener.packetSent(this, sentPacket);
			}
		} catch (IOException ioe) {
			close();
			listener.socketException(this, ioe);
		}
	}

	public SocketChannel getSocketChannel() {
		return sc;
	}

	public String toString() {  
		return Integer.toString(sc.socket().getLocalPort());
	}
}