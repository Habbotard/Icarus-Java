package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.entity.AbstractRoomEntity;
import org.alexdev.icarus.game.room.model.Point;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.netty.readers.Response;

public class TalkMessageComposer extends Response {

	public TalkMessageComposer(AbstractRoomEntity roomUser, boolean shout, String message, int count, int textColour) {

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

		if (!roomUser.isWalking()) {
			Point point = roomUser.getPoint();
			roomUser.setRotation(Rotation.calculate(point.getX(), point.getY(), point.getX(), point.getY()), true);
			roomUser.updateStatus();
		}

	}

}
