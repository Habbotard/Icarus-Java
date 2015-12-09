package net.quackster.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.quackster.icarus.game.pathfinder.Point;
import net.quackster.icarus.game.room.RoomUser;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.netty.readers.Response;

import net.quackster.icarus.game.room.models.Rotation;

public class TalkMessageComposer extends Response {

	
	public TalkMessageComposer(RoomUser roomUser, boolean shout, String message, int count, int textColour) {

		List<Integer> allowedColours = new ArrayList<Integer>(Arrays.asList(new Integer[] { 0, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 29 }));
		
		if (!allowedColours.contains(textColour)) {
			textColour = 0;
		}

		int header = Outgoing.ChatMessageComposer;

		if (shout) {
			header = Outgoing.ShoutMessageComposer;
		}

		// TODO: Filtering/anti-flodding
		// TODO: Chat logs
		
		// TODO: If bot then chat colour = 2
		
        this.init(header);
        this.appendInt32(roomUser.getVirtualId());
        this.appendString(message);
        this.appendInt32(0);
        this.appendInt32(textColour);
        this.appendInt32(0);// links count (foreach string string bool)
        this.appendInt32(count);
        
        Point point = roomUser.getPoint();
        
        int head = Rotation.calculate(point.getX(), point.getY(), point.getX(), point.getY());
        
        roomUser.setRotation(head, true);
        roomUser.updateStatus();
		
	}

}
