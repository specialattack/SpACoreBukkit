package net.specialattack.bukkit.core.games;

import java.util.UUID;

import com.mojang.NBT.NBTTagCompound;

import net.specialattack.bukkit.core.SpACore;
import net.specialattack.bukkit.core.block.Cuboid;

/**
 * Interface for loading playgrounds.
 *
 * @see SpACore#registerPlaygroundType(String, IPlaygroundLoader)
 */
public interface IPlaygroundLoader {

    /**
     * Passed on by {@link SpACore#loadPlayground(String, Cuboid)} to create an
     * instance of a PlayGround.
     *
     * @param cuboid
     *         The cuboid area of the playground.
     *
     * @return A new playground instance.
     */
    public Playground createInstance(Cuboid cuboid);
    
    /**
     * Passed on by {@link SpACore#loadPlayground(String, Cuboid)} to load an
     * instance of a PlayGround that has already been saved.
     *
     * @param cuboid
     *         The cuboid area of the playground.
     *
     * @return A new playground instance.
     */
    public Playground loadSavedPlayground(UUID id, NBTTagCompound data);
}
