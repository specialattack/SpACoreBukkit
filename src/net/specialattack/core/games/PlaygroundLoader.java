
package net.specialattack.core.games;

import net.specialattack.core.block.Cuboid;

public abstract class PlaygroundLoader {
    public abstract Playground createInstance(Cuboid cuboid);
}
