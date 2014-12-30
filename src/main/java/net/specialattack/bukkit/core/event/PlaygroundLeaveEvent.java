package net.specialattack.bukkit.core.event;

import net.specialattack.bukkit.core.games.Playground;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Author: Matt Date: 30 Dec 2014 Time: 5:46:08 pm (C) mbl111 2014
 **/

public class PlaygroundLeaveEvent extends BasePlayerPlaygroundEvent implements Cancellable {

	private boolean isCancelled = false;

	public PlaygroundLeaveEvent(Playground playground, Player player) {
		super(playground, player);
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		isCancelled = cancel;
	}

}
