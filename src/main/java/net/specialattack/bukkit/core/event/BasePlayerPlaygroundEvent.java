package net.specialattack.bukkit.core.event;

import net.specialattack.bukkit.core.games.Playground;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Author: Matt Date: 30 Dec 2014 Time: 5:57:56 pm (C) mbl111 2014
 **/

class BasePlayerPlaygroundEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Playground playground;
	private final Player player;

	public BasePlayerPlaygroundEvent(final Playground playground, final Player player) {
		this.player = player;
		this.playground = playground;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Playground getPlayground() {
		return playground;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
