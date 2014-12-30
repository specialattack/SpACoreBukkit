package net.specialattack.bukkit.core.util;

public abstract class Function<P> extends Func<P, Void> {

    public abstract void run(P value);

    public final Void go(P value) {
        this.run(value);

        return null;
    }

}
