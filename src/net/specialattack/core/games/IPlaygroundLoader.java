
package net.specialattack.core.games;

import net.specialattack.core.block.Cuboid;

public interface IPlaygroundLoader {
    public abstract Playground createInstance(Cuboid cuboid);
}
