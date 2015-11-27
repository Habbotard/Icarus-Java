package net.quackster.icarus.network.handlers.listeners;

import java.nio.ByteBuffer;

import net.quackster.icarus.network.PacketChannel;

public class NetworkListener implements PacketChannelListener {

	public void packetArrived(PacketChannel pc, Object pckt) {

		if (pckt instanceof ByteBuffer) {
			pc.sendPacket((ByteBuffer)pckt);
		}
	}

	public void socketException(PacketChannel pc, Exception ex) {
		System.out.println("[" + pc.toString() + "] Error: " + ex.getMessage());    
	}

	public void socketDisconnected(PacketChannel pc) {
		System.out.println("[" + pc.toString() + "] Disconnected.");
	}

	public void packetSent(PacketChannel pc, ByteBuffer pckt) {
		try {
			pc.resumeReading();
		} catch (Exception e) {    
			e.printStackTrace();
		}
	}
}