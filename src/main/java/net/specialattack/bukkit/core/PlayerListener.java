package net.specialattack.bukkit.core;

import net.specialattack.bukkit.core.event.PlaygroundEnterEvent;
import net.specialattack.bukkit.core.event.PlaygroundLeaveEvent;
import net.specialattack.bukkit.core.games.Playground;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/** 
	Author: Matt
	Date: 30 Dec 2014
	Time: 5:44:35 pm
	(C) mbl111 2014
 **/

public class PlayerListener implements Listener {
 
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		
		if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;
		
		Playground fromPlayground = SpACore.instance.getPlaygroundPool().insidePlayground(event.getFrom());
		Playground toPlayground = SpACore.instance.getPlaygroundPool().insidePlayground(event.getTo());
		
		boolean wasInPlayground = fromPlayground != null;
		boolean movedToPlayground = toPlayground != null;
		
		if (!wasInPlayground && !movedToPlayground){
			return;
		}
		
		if (wasInPlayground && !movedToPlayground){
			//Playground has been left;
			Bukkit.getPluginManager().callEvent(new PlaygroundLeaveEvent(fromPlayground, event.getPlayer()));
			return;
		}
		
		if (!wasInPlayground && movedToPlayground){
			//We enter a playground;
			Bukkit.getPluginManager().callEvent(new PlaygroundEnterEvent(toPlayground, event.getPlayer()));
			return;
		}
		
		
		if (fromPlayground.equals(toPlayground)) return;
		
		Bukkit.getPluginManager().callEvent(new PlaygroundLeaveEvent(fromPlayground, event.getPlayer()));
		Bukkit.getPluginManager().callEvent(new PlaygroundEnterEvent(toPlayground, event.getPlayer()));
		
	}
	
}
