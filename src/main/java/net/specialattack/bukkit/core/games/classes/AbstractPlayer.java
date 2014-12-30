package net.specialattack.bukkit.core.games.classes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class AbstractPlayer {

    public final String name;
    public boolean errored = false; // Use me to determine if the player is still playing, if this is true the player shouldn't be messed with

    public AbstractPlayer(String name) {
        this.name = name;
    }

    public Player tryGetPlayer() {
        return Bukkit.getPlayer(this.name);
    }

}
