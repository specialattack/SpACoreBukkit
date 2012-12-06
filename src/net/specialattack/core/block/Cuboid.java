
package net.specialattack.core.block;

import org.bukkit.Location;
import org.bukkit.World;

public final class Cuboid {
    private final Location start;
    private final Location end;

    public Cuboid(Location startPoint, Location endPoint) {
        if (!startPoint.getWorld().equals(endPoint.getWorld())) {
            throw new UnsupportedOperationException("Cannot initialize cuboid in 2 different worlds");
        }

        this.start = startPoint;
        this.end = endPoint;

        if (this.end.getX() < this.start.getX()) {
            double x = this.start.getX();
            this.start.setX(this.end.getX());
            this.end.setX(x);
        }

        if (this.end.getY() < this.start.getY()) {
            double y = this.start.getY();
            this.start.setY(this.end.getY());
            this.end.setY(y);
        }

        if (this.end.getZ() < this.start.getZ()) {
            double z = this.start.getZ();
            this.start.setZ(this.end.getZ());
            this.end.setZ(z);
        }
    }

    public Cuboid(World world, int startX, int startY, int startZ, int endX, int endY, int endZ) {
        this.start = new Location(world, startX, startY, startZ);
        this.end = new Location(world, endX, endY, endZ);

        if (this.end.getX() < this.start.getX()) {
            double x = this.start.getX();
            this.start.setX(this.end.getX());
            this.end.setX(x);
        }

        if (this.end.getY() < this.start.getY()) {
            double y = this.start.getY();
            this.start.setY(this.end.getY());
            this.end.setY(y);
        }

        if (this.end.getZ() < this.start.getZ()) {
            double z = this.start.getZ();
            this.start.setZ(this.end.getZ());
            this.end.setZ(z);
        }
    }

    public World getWorld() {
        return this.start.getWorld();
    }

    public Location getStart() {
        return this.start.clone();
    }

    public Location getEnd() {
        return this.end.clone();
    }

    public int getStartX() {
        return this.start.getBlockX();
    }

    public int getStartY() {
        return this.start.getBlockY();
    }

    public int getStartZ() {
        return this.start.getBlockZ();
    }

    public int getEndX() {
        return this.end.getBlockX();
    }

    public int getEndY() {
        return this.end.getBlockY();
    }

    public int getEndZ() {
        return this.end.getBlockZ();
    }

    public int getSizeX() {
        return this.end.getBlockX() - this.start.getBlockX();
    }

    public int getSizeY() {
        return this.end.getBlockY() - this.start.getBlockY();
    }

    public int getSizeZ() {
        return this.end.getBlockZ() - this.start.getBlockZ();
    }

    public int getSize() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }
}
