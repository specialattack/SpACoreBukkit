package net.specialattack.bukkit.core.games;

import net.specialattack.bukkit.core.SpACore;
import net.specialattack.bukkit.core.block.Cuboid;

/**
 * Interface for loading playgrounds.
 *
 * @author heldplayer
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
    public abstract Playground createInstance(Cuboid cuboid);
}
