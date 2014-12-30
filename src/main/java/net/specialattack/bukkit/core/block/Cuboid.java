package net.specialattack.bukkit.core.block;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * Class to define a cubic area in the world.
 */
public final class Cuboid {

    private final Location start;
    private final Location end;

    /**
     * Constructor that takes 2 locations that must be in the same world to
     * define a cubic area.
     *
     * @param startPoint
     *         The starting point of the cuboid.
     * @param endPoint
     *         The ending point of the cuboid.
     *
     * @throws UnsupportedOperationException
     *         Thrown if the supplied locations are not in the same world.
     */
    public Cuboid(Location startPoint, Location endPoint) throws UnsupportedOperationException {
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

    /**
     * Constructor that takes raw coordinates and a world pointer to define a
     * cubid area.
     *
     * @param world
     *         The world to define in.
     * @param startX
     *         The start X coordinate.
     * @param startY
     *         The start Y coordinate.
     * @param startZ
     *         The start Z coordinate.
     * @param endX
     *         The end X coordinate.
     * @param endY
     *         The end Y coordinate.
     * @param endZ
     *         The end Z coordinate.
     */
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

    /**
     * Gets the world this cuboid is defined in.
     *
     * @return The world in which the cuboid is in.
     */
    public World getWorld() {
        return this.start.getWorld();
    }

    /**
     * Gets the starting position of the cuboid.
     *
     * @return The starting position of the cuboid.
     */
    public Location getStart() {
        return this.start.clone();
    }

    /**
     * Gets the ending position of the cuboid.
     *
     * @return The ending position of the cuboid.
     */
    public Location getEnd() {
        return this.end.clone();
    }

    /**
     * Gets the starting X position of the cuboid.
     *
     * @return The starting X position of the cuboid.
     */
    public int getStartX() {
        return this.start.getBlockX();
    }

    /**
     * Gets the starting Y position of the cuboid.
     *
     * @return The starting Y position of the cuboid.
     */
    public int getStartY() {
        return this.start.getBlockY();
    }

    /**
     * Gets the starting Z position of the cuboid.
     *
     * @return The starting Z position of the cuboid.
     */
    public int getStartZ() {
        return this.start.getBlockZ();
    }

    /**
     * Gets the ending X position of the cuboid.
     *
     * @return The ending X position of the cuboid.
     */
    public int getEndX() {
        return this.end.getBlockX();
    }

    /**
     * Gets the ending Y position of the cuboid.
     *
     * @return The ending Y position of the cuboid.
     */
    public int getEndY() {
        return this.end.getBlockY();
    }

    /**
     * Gets the ending Z position of the cuboid.
     *
     * @return The ending Z position of the cuboid.
     */
    public int getEndZ() {
        return this.end.getBlockZ();
    }

    /**
     * Gets the size of the cuboid in the X axis.
     *
     * @return The size of the cuboid in the X axis.
     */
    public int getSizeX() {
        return this.end.getBlockX() - this.start.getBlockX();
    }

    /**
     * Gets the size of the cuboid in the Y axis.
     *
     * @return The size of the cuboid in the Y axis.
     */
    public int getSizeY() {
        return this.end.getBlockY() - this.start.getBlockY();
    }

    /**
     * Gets the size of the cuboid in the Z axis.
     *
     * @return The size of the cuboid in the Z axis.
     */
    public int getSizeZ() {
        return this.end.getBlockZ() - this.start.getBlockZ();
    }

    /**
     * Gets the total size of the cuboid.
     *
     * @return The total size of the cuboid.
     */
    public int getSize() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ();
    }
}
