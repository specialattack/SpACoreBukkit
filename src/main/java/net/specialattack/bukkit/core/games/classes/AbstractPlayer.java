package net.specialattack.bukkit.core.games.classes;

import java.util.List;
import java.util.logging.Level;
import net.specialattack.bukkit.core.PlayerStorage;
import net.specialattack.bukkit.core.SpACore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author mbl111
 */
public abstract class AbstractPlayer {

    public final String name;
    public boolean errored = false; // Use me to determine if the player is still playing, if this is true the player shouldn't be messed with

    public AbstractPlayer(String name) {
        this.name = name;
    }

    public Player tryGetPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    public abstract void addToLoadout(ItemStack equiptment);

    public abstract List<ItemStack> getLoadout();

    public void loadoutPlayer() {
        Player player = this.tryGetPlayer();

        try {
            PlayerStorage.store(player);
            this.doLoadout();
        } catch (Exception e) {
            this.errored = true;
            player.sendMessage(ChatColor.RED + "An error occoured, please contact mbl111 or heldplayer");
            SpACore.log(Level.SEVERE, "Error occoured while trying to save player inventory", e);
        }
    }

    protected abstract void doLoadout();
}
