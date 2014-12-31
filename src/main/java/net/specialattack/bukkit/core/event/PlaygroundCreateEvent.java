package net.specialattack.bukkit.core.event;

import net.specialattack.bukkit.core.games.Playground;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/** 
	Author: Matt
	Date: 30 Dec 2014
	Time: 5:53:04 pm
	(C) mbl111 2014
 **/

public class PlaygroundCreateEvent extends BasePlayerPlaygroundEvent implements Cancellable{

	private boolean isCancelled = false;
	private String cancelReason = "";
	
	public PlaygroundCreateEvent(Playground playground, Player player) {
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
	
	public void setCancelReason(String reason){
		this.cancelReason = reason;
	}
	
	public String getCancelReason() {
		return cancelReason;
	}

	
	
}
