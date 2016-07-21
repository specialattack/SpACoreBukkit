package net.specialattack.spacore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerPermissionsChangedEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public PlayerPermissionsChangedEvent(Player player) {
        super(player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused") // Required by bukkit
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
