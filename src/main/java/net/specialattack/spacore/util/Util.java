package net.specialattack.spacore.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

/**
 * Utility class for a bunch of random things.
 */
public final class Util {

    public static final List<String> TAB_RESULT_EMPTY = Collections.emptyList();
    public static final List<String> TAB_RESULT_TRUE_FALSE = Collections.unmodifiableList(Arrays.asList("true", "false"));

    private Util() {
    }

    /**
     * String sensitive version of {@link Util#isInArray(Object[], Object)}
     *
     * @param array
     *         The array to check against.
     * @param toCheck
     *         The value to check for.
     *
     * @return True if <code>array</code> contains <code>toCheck</code>
     *
     * @see Util#isInArray(Object[], Object)
     */
    public static boolean isInArray(String[] array, String toCheck) {
        for (String value : array) {
            if (value.equalsIgnoreCase(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for checking if an Object is in a specified Object array.
     *
     * @param array
     *         The array to check against.
     * @param toCheck
     *         The value to check for.
     *
     * @return True if <code>array</code> contains <code>toCheck</code>
     */
    public static <E> boolean isInArray(E[] array, E toCheck) {
        for (E value : array) {
            if (value.equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Joins an iterable of strings to a single string, combined with commas
     *
     * @param strings
     *         The iterable to join
     *
     * @return A string that contains all the elements of the iterable combined using commas
     */
    public static String join(Iterable<String> strings) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String str : strings) {
            if (!first) {
                result.append(", ");
            } else {
                first = false;
            }
            result.append(str);
        }
        return result.toString();
    }

    /**
     * Joins an array of strings to a single string, combined with commas
     *
     * @param strings
     *         The array to join
     *
     * @return A string that contains all the elements of the array combined using commas
     */
    public static String join(String... strings) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String str : strings) {
            if (!first) {
                result.append(", ");
            } else {
                first = false;
            }
            result.append(str);
        }
        return result.toString();
    }

    /**
     * Matches entities or players using as many methods as possible.
     *
     * @param input
     *         The input string to parse to select players.
     * @param origin
     *         The origin for finding players, used when the input is a selector.
     * @param forceType
     *         The type to force the entities to be, or null to not force a type.
     *
     * @return A set of all entities that were matched by the input.
     *
     * @throws java.lang.IllegalArgumentException
     *         When the input is invalid.
     */
    public static Set<Entity> matchEntities(String input, Location origin, EntityType forceType) {
        Set<Entity> result = new LinkedHashSet<>();

        String[] splitInput = input.split(" ");

        for (String current : splitInput) {
            if (current.charAt(0) == '@') { // Try to use @a @e @p or @r
                EntitySelector selector = new EntitySelector();
                if (origin != null) {
                    selector.setLocation(origin);
                }
                switch (current.charAt(1)) {
                    case 'r':
                    case 'R':
                        selector.selectRandom = true;
                    case 'p':
                    case 'P':
                        selector.setCount(1);
                    case 'a':
                    case 'A':
                        selector.setType(EntityType.PLAYER, false);
                        break;
                    case 'e':
                    case 'E':
                        selector.setType(null, false);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown selector type");
                }
                if (current.length() > 2) {
                    if (current.charAt(2) != '[' || current.charAt(current.length() - 1) != ']') {
                        throw new IllegalArgumentException("Selector isn't properly enclosed with braces");
                    }
                    String[] parameters = current.substring(3, current.length() - 1).split(",");
                    for (String parameter : parameters) {
                        String[] split = parameter.split("=", 2);
                        if (split.length != 2) {
                            throw new IllegalArgumentException("Parameter '" + parameter + "' is not formed as 'key=value'");
                        }

                        try {
                            if (split[0].equalsIgnoreCase("x")) {
                                selector.setX(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("y")) {
                                selector.setY(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("z")) {
                                selector.setZ(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("r")) {
                                selector.setRadiusMax(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("rm")) {
                                selector.setRadiusMin(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("m")) {
                                selector.setGameMode(Integer.parseInt(split[1]));
                            } else if (split[0].equalsIgnoreCase("c")) {
                                selector.setCount(Integer.parseInt(split[1]));
                            } else if (split[0].equalsIgnoreCase("l")) {
                                selector.setLevelMax(Integer.parseInt(split[1]));
                            } else if (split[0].equalsIgnoreCase("lm")) {
                                selector.setLevelMin(Integer.parseInt(split[1]));
                            } else if (split[0].equalsIgnoreCase("team")) {
                                if (split[1].length() > 0 && split[1].charAt(0) == '!') {
                                    selector.setTeam(split[1].substring(1), true);
                                } else {
                                    selector.setTeam(split[1], false);
                                }
                            } else if (split[0].equalsIgnoreCase("name")) {
                                if (split[1].length() > 0 && split[1].charAt(0) == '!') {
                                    selector.setName(split[1].substring(1), true);
                                } else {
                                    selector.setName(split[1], false);
                                }
                            } else if (split[0].equalsIgnoreCase("dx")) {
                                selector.setDx(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("dy")) {
                                selector.setDy(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("dz")) {
                                selector.setDz(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("rx")) {
                                selector.setRotationXMax(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("rxm")) {
                                selector.setRotationXMin(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("ry")) {
                                selector.setRotationYMax(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("rym")) {
                                selector.setRotationYMin(Double.parseDouble(split[1]));
                            } else if (split[0].equalsIgnoreCase("type")) {
                                if (split[1].length() > 0 && split[1].charAt(0) == '!') {
                                    String typeName = split[1].substring(1);
                                    @SuppressWarnings("deprecation") EntityType type = EntityType.fromName(typeName);
                                    if (type == null && !split[1].isEmpty()) {
                                        throw new IllegalArgumentException("'" + typeName + "' is not a valid entity type");
                                    }
                                    selector.setType(type, false);
                                } else {
                                    @SuppressWarnings("deprecation") EntityType type = EntityType.fromName(split[1]);
                                    if (type == null && !split[1].isEmpty()) {
                                        throw new IllegalArgumentException("'" + split[1] + "' is not a valid entity type");
                                    }
                                    selector.setType(type, false);
                                }
                            }
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Malformed parameter for '" + parameter + "'", e);
                        }
                    }
                }

                if (forceType != null) {
                    selector.setType(forceType, false);
                }

                Set<Entity> entities = new HashSet<>();
                if (origin != null) {
                    entities.addAll(origin.getWorld().getEntities());
                } else {
                    for (World world : Bukkit.getWorlds()) {
                        entities.addAll(world.getEntities());
                    }
                }
                selector.filterEntities(entities, result);
            } else {
                String lowerInput = current.toLowerCase();
                if (forceType == EntityType.PLAYER) {
                    if (current.length() > 8) {
                        try {
                            Player player = Bukkit.getPlayer(UUID.fromString(current));
                            if (player != null) {
                                result.add(player);
                            }
                            continue;
                        } catch (IllegalArgumentException ignored) {
                        }
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String lower = player.getName().toLowerCase();
                        if (lowerInput.equals(lower)) {
                            result.clear();
                            result.add(player);
                        } else if (lowerInput.startsWith(lower)) {
                            result.add(player);
                        }
                    }
                } else {
                    Set<Entity> entities = new HashSet<>();
                    if (origin != null) {
                        entities.addAll(origin.getWorld().getEntities());
                    } else {
                        for (World world : Bukkit.getWorlds()) {
                            entities.addAll(world.getEntities());
                        }
                    }

                    UUID uuid = null;
                    try {
                        uuid = UUID.fromString(current);
                    } catch (IllegalArgumentException ignored) {
                    }
                    for (Entity entity : entities) {
                        String lower = entity instanceof Player ? entity.getName() : entity.getCustomName();
                        if (forceType != null && entity.getType() != forceType && lower == null) {
                            continue;
                        }
                        if (uuid != null && entity.getUniqueId().equals(uuid)) {
                            result.add(entity);
                            continue;
                        }
                        if (lower != null) {
                            lower = lower.toLowerCase();
                            if (lowerInput.equals(lower) || lower.startsWith(lowerInput) || (current.length() > 8 && entity.getUniqueId().toString().startsWith(lowerInput))) {
                                result.add(entity);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Helper class for selecting entities.
     */
    private static class EntitySelector {

        private boolean selectRandom;
        private boolean inWorld;
        private double x, y, z;
        private boolean hasRadius;
        private double radiusMax = -1, radiusMin = -1;
        private int gameMode = -1;
        private int count = Integer.MAX_VALUE;
        private boolean hasLevels;
        private int levelMax = -1, levelMin = -1;
        // score_name and score_name_min ?
        private boolean inverseTeam;
        private String team;
        private boolean inverseName;
        private String name;
        private boolean hasVolume;
        private double dx, dy, dz;
        private boolean hasRotationX;
        private double rotationXMax = 90.0D, rotationXMin = -90.0D;
        private boolean hasRotationY;
        private double rotationYMax = -180.0D, rotationYMin = 180.0D;
        private boolean inverseType;
        private EntityType type;

        public Collection<Entity> filterEntities(Set<Entity> input, Collection<Entity> output) {
            if (output == null) {
                output = new HashSet<>();
            } else {
                output.clear();
            }

            for (Entity entity : input) {
                if (this.type != null) {
                    if (this.inverseType) { // First filter type as that is probably fastest
                        if (this.type == entity.getType()) {
                            continue;
                        }
                    } else {
                        if (this.type != entity.getType()) {
                            continue;
                        }
                    }
                }
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    //noinspection deprecation
                    if (this.gameMode >= 0 && player.getGameMode().getValue() != this.gameMode) {
                        continue;
                    }

                    if (this.hasLevels) {
                        if (this.levelMax >= 0 && player.getLevel() > this.levelMax) {
                            continue;
                        }
                        if (this.levelMin >= 0 && player.getLevel() < this.levelMin) {
                            continue;
                        }
                    }

                    if (this.team != null) {
                        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team team = scoreboard.getPlayerTeam(player);
                        String name = team == null ? null : team.getName();
                        if (this.inverseTeam) {
                            if (this.team.isEmpty() && (name == null || name.isEmpty())) { // team=!
                                continue;
                            }
                            if (this.team.equalsIgnoreCase(name)) { // team=!team
                                continue;
                            }
                        } else {
                            if (this.team.isEmpty() && name != null && !name.isEmpty()) { // team= FIXME
                                continue;
                            }
                            if (!this.team.equalsIgnoreCase(name)) { // team=team
                                continue;
                            }
                        }
                    }
                }

                if (this.name != null) {
                    String name = entity instanceof Player ? entity.getName() : entity.getCustomName();
                    System.out.println(entity.getType() + ": " + name);
                    if (this.inverseName) {
                        if (this.name.isEmpty() && (name == null || name.isEmpty())) { // name=!
                            continue;
                        }
                        if (this.name.equalsIgnoreCase(name)) { // name=!name
                            continue;
                        }
                    } else {
                        if (this.name.isEmpty() && name != null && !name.isEmpty()) { // name= FIXME
                            continue;
                        }
                        if (!this.name.equalsIgnoreCase(name)) { // name=!name
                            continue;
                        }
                    }
                }

                Location location = entity.getLocation();

                if (this.hasRotationX) {
                    double pitch = location.getPitch();
                    if (pitch < this.rotationXMin || pitch > this.rotationXMax) {
                        continue;
                    }
                }
                if (this.hasRotationY) {
                    double yaw = location.getYaw() - 180.0F;
                    if (yaw < this.rotationYMin || yaw > this.rotationYMax) {
                        continue;
                    }
                }

                if (this.inWorld) {
                    double dX = this.x - location.getX();
                    double dY = this.y - location.getY();
                    double dZ = this.z - location.getZ();

                    if (this.hasRadius) {
                        double dist = dX * dX + dY * dY + dZ * dZ;
                        if (this.radiusMin > 0 && dist < this.radiusMin * this.radiusMin) {
                            continue;
                        }
                        if (this.radiusMax > 0 && dist > this.radiusMax * this.radiusMax) {
                            continue;
                        }
                    }
                    if (this.hasVolume) {
                        if (dX > this.dx || dX < -this.dx) {
                            continue;
                        }
                        if (dY > this.dy || dY < -this.dy) {
                            continue;
                        }
                        if (dZ > this.dz || dZ < -this.dz) {
                            continue;
                        }
                    }
                }

                output.add(entity);
            }

            if (this.count != Integer.MAX_VALUE) {
                Collection<Entity> filter = new TreeSet<>((o1, o2) -> {
                    Location loc1 = o1.getLocation();
                    Location loc2 = o2.getLocation();
                    double dX1 = EntitySelector.this.x - loc1.getX();
                    double dY1 = EntitySelector.this.y - loc1.getY();
                    double dZ1 = EntitySelector.this.z - loc1.getZ();
                    double dX2 = EntitySelector.this.x - loc2.getX();
                    double dY2 = EntitySelector.this.y - loc2.getY();
                    double dZ2 = EntitySelector.this.z - loc2.getZ();
                    double dist1 = dX1 * dX1 + dY1 * dY1 + dZ1 * dZ1;
                    double dist2 = dX2 * dX2 + dY2 * dY2 + dZ2 * dZ2;
                    if (dist1 == dist2) {
                        return Integer.compare(o1.getEntityId(), o2.getEntityId());
                    }
                    return Double.compare(dist1, dist2);
                });

                filter.addAll(output);
                output.clear();

                if (this.selectRandom) {
                    ArrayList<Entity> temp = new ArrayList<>(filter.size());
                    temp.addAll(filter);
                    filter = temp;
                    Collections.shuffle(temp);
                }

                int i = 0;
                for (Entity entity : filter) {
                    if (this.count > 0 ? i >= this.count : i <= -this.count) {
                        break;
                    }
                    output.add(entity);
                    i++;
                }
            }

            return output;
        }

        public void setLocation(Location location) {
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.inWorld = true;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public void setRadiusMax(double radiusMax) {
            this.radiusMax = radiusMax;
            this.hasRadius = true;
        }

        public void setRadiusMin(double radiusMin) {
            this.radiusMin = radiusMin;
            this.hasRadius = true;
        }

        public void setGameMode(int gameMode) {
            this.gameMode = gameMode;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setLevelMax(int levelMax) {
            this.levelMax = levelMax;
            this.hasLevels = true;
        }

        public void setLevelMin(int levelMin) {
            this.levelMin = levelMin;
            this.hasLevels = true;
        }

        public void setTeam(String team, boolean inverse) {
            this.team = team;
            this.inverseTeam = inverse;
        }

        public void setName(String name, boolean inverse) {
            this.name = name;
            this.inverseName = inverse;
        }

        public void setDx(double dx) {
            this.dx = dx;
            this.hasVolume = true;
        }

        public void setDy(double dy) {
            this.dy = dy;
            this.hasVolume = true;
        }

        public void setDz(double dz) {
            this.dz = dz;
            this.hasVolume = true;
        }

        public void setRotationXMax(double rotationXMax) {
            this.rotationXMax = rotationXMax;
            this.hasRotationX = true;
        }

        public void setRotationXMin(double rotationXMin) {
            this.rotationXMin = rotationXMin;
            this.hasRotationX = true;
        }

        public void setRotationYMax(double rotationYMax) {
            this.rotationYMax = rotationYMax;
            this.hasRotationY = true;
        }

        public void setRotationYMin(double rotationYMin) {
            this.rotationYMin = rotationYMin;
            this.hasRotationY = true;
        }

        public void setType(EntityType type, boolean inverse) {
            this.type = type;
            this.inverseType = inverse;
        }
    }
}
