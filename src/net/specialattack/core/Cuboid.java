
package net.specialattack.core;

import org.bukkit.Location;
import org.bukkit.World;

public class Cuboid {
    private final Location start;
    private final Location end;

    public Cuboid(Location startPoint, Location endPoint) {
        if (!startPoint.getWorld().equals(endPoint.getWorld())) {
            throw new UnsupportedOperationException("Cannot initialize cuboid in 2 different worlds");
        }

        start = startPoint;
        end = endPoint;

        if (end.getX() < start.getX()) {
            double x = start.getX();
            start.setX(end.getX());
            end.setX(x);
        }

        if (end.getY() < start.getY()) {
            double y = start.getY();
            start.setY(end.getY());
            end.setY(y);
        }

        if (end.getZ() < start.getZ()) {
            double z = start.getZ();
            start.setZ(end.getZ());
            end.setZ(z);
        }
    }

    public World getWorld() {
        return start.getWorld();
    }

    public Location getStart() {
        return start.clone();
    }

    public Location getEnd() {
        return end.clone();
    }

    public int getStartX() {
        return start.getBlockX();
    }

    public int getStartY() {
        return start.getBlockY();
    }

    public int getStartZ() {
        return start.getBlockZ();
    }

    public int getEndX() {
        return end.getBlockX();
    }

    public int getEndY() {
        return end.getBlockY();
    }

    public int getEndZ() {
        return end.getBlockZ();
    }

    public int getSizeX() {
        return end.getBlockX() - start.getBlockX();
    }

    public int getSizeY() {
        return end.getBlockY() - start.getBlockY();
    }

    public int getSizeZ() {
        return end.getBlockZ() - start.getBlockZ();
    }

    public int getSize() {
        return getSizeX() * getSizeY() * getSizeZ();
    }
}
