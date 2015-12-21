package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.user.Session;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.outgoing.navigator.FlatCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorCategories;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorMetaDataComposer;
import org.alexdev.icarus.netty.readers.Request;
import org.alexdev.icarus.netty.readers.Response;

public class NewNavigatorMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, Request request) {

		Response response = new Response();
		response.init(Outgoing.NavigatorLiftedRoomsComposer);
		response.appendInt32(0);
		session.send(response);

		response.init(Outgoing.NavigatorSavedSearchesComposer);
		response.appendInt32(1);
		response.appendInt32(1);
		response.appendString("myworld_view");
		response.appendString("test3");
		response.appendString("");
		session.send(response);

		response.init(Outgoing.NewNavigatorSizeMessageComposer);
		response.appendInt32(50);//pref.NewnaviX);
		response.appendInt32(50);//pref.NewnaviY);
		response.appendInt32(580);//pref.NewnaviWidth);
		response.appendInt32(600);//pref.NewnaviHeight);
		response.appendBoolean(true);
		response.appendInt32(1);
		session.send(response);

		session.send(new NavigatorMetaDataComposer());
		session.send(new FlatCategoriesMessageComposer());
		session.send(new NavigatorCategories());
		session.getConnection().setSentNewNavigator(true);

	}

}
