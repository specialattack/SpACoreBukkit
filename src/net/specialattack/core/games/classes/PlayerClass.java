
package net.specialattack.core.games.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerClass {

    public List<ItemStack> loadout = new ArrayList<ItemStack>();
    private String name;

    public PlayerClass(String name) {
        this.name = name;
    }

    public void addEquiptmentToLoadout(ItemStack equiptment) {
        this.loadout.add(equiptment);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemStack> getLoadout() {
        return this.loadout;
    }

    public void loadoutPlayer(Player player) {
        PlayerInventory i = player.getInventory();
        i.clear();
        for (ItemStack is : this.loadout) {
            i.addItem(is);
        }
    }

}
