package net.specialattack.bukkit.core.games.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.specialattack.bukkit.core.PlayerStorage;
import net.specialattack.bukkit.core.SpACore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class GamePlayer extends AbstractPlayer implements EquipmentHolder {

    private List<ItemStack> mainLoadout = new ArrayList<ItemStack>();
    private ItemStack[] armourLoadout = new ItemStack[4];

    public GamePlayer(String name) {
        super(name);
    }

    public void loadoutPlayer(String stash) {
        Player player = this.tryGetPlayer();
        this.doLoadout();
        try {
            PlayerStorage.store(player, stash);
            this.doLoadout();
        } catch (Exception e) {
            this.errored = true;
            player.sendMessage(ChatColor.RED + "An error occoured, please contact mbl111 or heldplayer");
            SpACore.log(Level.SEVERE, "Error occoured while trying to save player inventory", e);
        }
    }

    @Override
    public void addToArmourLoadout(ItemStack equiptment, int slot) {

    }

    @Override
    public void addToLoadout(ItemStack equiptment) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doLoadout() {
        // TODO Auto-generated method stub

    }

    @Override
    public ItemStack[] getArmourLoadout() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ItemStack> getLoadout() {
        return null;
    }

}
