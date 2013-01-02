
package net.specialattack.core.games;

import net.specialattack.core.SpACore;
import net.specialattack.core.block.Cuboid;

/**
 * Interface for loading playgrounds.
 * 
 * @see SpACore#registerPlaygroundType(String, IPlaygroundLoader)
 * @author heldplayer
 * 
 */
public interface IPlaygroundLoader {

    /**
     * Passed on by {@link SpACore#loadPlayground(String, Cuboid)} to create an
     * instance of a PlayGround.
     * 
     * @param cuboid
     *        The cuboid area of the playground.
     * @return A new playground instance.
     */
    public abstract Playground createInstance(Cuboid cuboid);
}
