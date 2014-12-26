package net.specialattack.bukkit.core.games.classes;

import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 * Author: Matt
 * Date: 25 Dec 2014
 * Time: 4:31:25 pm
 * (C) mbl111 2014
 */

public interface EquipmentHolder {

    public void addToLoadout(ItemStack equiptment);

    /**
     * @param equiptment
     * @param slot
     */
    public void addToArmourLoadout(ItemStack equiptment, int slot);

    public List<ItemStack> getLoadout();

    public ItemStack[] getArmourLoadout();

    void doLoadout();

}
