package net.quackster.icarus.messages.incoming.navigator;

import net.quackster.icarus.game.user.Session;
import net.quackster.icarus.messages.Message;
import net.quackster.icarus.messages.headers.Outgoing;
import net.quackster.icarus.messages.outgoing.navigator.FlatCategoriesMessageComposer;
import net.quackster.icarus.messages.outgoing.navigator.NavigatorCategories;
import net.quackster.icarus.messages.outgoing.navigator.NavigatorMetaDataComposer;
import net.quackster.icarus.netty.readers.Request;
import net.quackster.icarus.netty.readers.Response;

public class NewNavigatorMessageEvent implements Message {

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
		response.appendInt32(0);//pref.NewnaviX);
		response.appendInt32(0);//pref.NewnaviY);
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
