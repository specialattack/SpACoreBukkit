package net.specialattack.bukkit.core.util;

import java.util.*;
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
     * method for clearing everything on a player. Does not modify location.
     *
     * @param player
     *         The player to clear.
     */
    public static void clearEverything(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.setLevel(0);
        player.setTotalExperience(0);
        player.setExhaustion(0.0F);
        player.setSaturation(20.0F);
        player.setExp(0.0F);
        player.getInventory().clear();
        player.setVelocity(new Vector(0, 0, 0));
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
     * Matches entities or players using as many methods as possible
     *
     * @param input
     *         The input string to parse to select players
     * @param origin
     *         The origin for finding players, used when the input is a selector
     * @param forceType
     *         The type to force the entities to be, or null to not force a type
     *
     * @return A set of all entities that were matched by the input
     *
     * @throws java.lang.IllegalArgumentException
     *         When the input is invalid
     */
    public static Set<Entity> matchEntities(String input, Location origin, EntityType forceType) {
        Set<Entity> result = new HashSet<Entity>();

        if (input.charAt(0) == '@') { // Try to use @a @e @p or @r
            PlayerSelector selector = new PlayerSelector();
            if (origin != null) {
                selector.setLocation(origin);
            }
            switch (input.charAt(1)) {
                case 'p':
                case 'P':
                case 'r':
                case 'R':
                    selector.setCount(1);
                case 'a':
                case 'A':
                    selector.setType(EntityType.PLAYER);
                    break;
                case 'e':
                case 'E':
                    selector.setType(null);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown selector type");
            }
            if (input.charAt(2) != '[' || input.charAt(input.length() - 1) != ']') {
                throw new IllegalArgumentException("Selector isn't properly enclosed with braces");
            }
            String[] parameters = input.substring(3, input.length() - 1).split(",");
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
                    } else if (split[0].equalsIgnoreCase("c")) {
                        selector.setCount(Integer.parseInt(split[1]));
                    } else if (split[0].equalsIgnoreCase("l")) {
                        selector.setLevelMax(Integer.parseInt(split[1]));
                    } else if (split[0].equalsIgnoreCase("lm")) {
                        selector.setLevelMin(Integer.parseInt(split[1]));
                    } else if (split[0].equalsIgnoreCase("team")) {
                        if (split[1].charAt(0) == '!') {
                            selector.setTeam(split[1].substring(1), true);
                        } else {
                            selector.setTeam(split[1], false);
                        }
                    } else if (split[0].equalsIgnoreCase("team")) {
                        if (split[1].charAt(0) == '!') {
                            selector.setTeam(split[1].substring(1), true);
                        } else {
                            selector.setTeam(split[1], false);
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
                        EntityType type = EntityType.fromName(split[1]);
                        if (type == null) {
                            throw new IllegalArgumentException("'" + parameter + "' is not a valid entity type");
                        }
                        selector.setType(type);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Malformed parameter for '" + parameter + "'", e);
                }

                Set<Entity> entities = new HashSet<Entity>();
                if (origin != null) {
                    entities.addAll(origin.getWorld().getEntities());
                } else {
                    for (World world : Bukkit.getWorlds()) {
                        entities.addAll(world.getEntities());
                    }
                }

                if (forceType != null) {
                    selector.setType(forceType);
                }
                selector.filterEntities(entities, result);
            }
        } else {
            String lowerInput = input.toLowerCase();
            if (forceType == EntityType.PLAYER) {
                try {
                    Player player = Bukkit.getPlayer(UUID.fromString(input));
                    if (player != null) {
                        result.add(player);
                        return result;
                    }
                } catch (IllegalArgumentException e) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String lower = player.getName().toLowerCase();
                        if (lowerInput.equals(lower)) {
                            result.clear();
                            result.add(player);
                            return result;
                        } else if (lowerInput.startsWith(lower)) {
                            result.add(player);
                        }
                    }
                }
            } else {
                Set<Entity> entities = new HashSet<Entity>();
                if (origin != null) {
                    entities.addAll(origin.getWorld().getEntities());
                } else {
                    for (World world : Bukkit.getWorlds()) {
                        entities.addAll(world.getEntities());
                    }
                }

                try {
                    UUID uuid = UUID.fromString(input);
                    for (Entity entity : entities) {
                        if ((forceType == null || entity.getType() == forceType) && entity.getUniqueId().equals(uuid)) {
                            result.add(entity);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    for (Entity entity : entities) {
                        String lower = entity.getCustomName();
                        if (forceType != null && entity.getType() != forceType && lower == null) {
                            continue;
                        }
                        lower = lower.toLowerCase();
                        if (lowerInput.equals(lower) || lower.startsWith(lowerInput)) {
                            result.add(entity);
                        }
                    }
                }
            }
        }

        return result;
    }

    private static class PlayerSelector {

        private boolean inWorld;
        private double x, y, z;
        private boolean hasRadius;
        private double radiusMax = -1, radiusMin = -1;
        private int gamemode = -1;
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
        private EntityType type;

        public Set<Entity> filterEntities(Set<Entity> input, Set<Entity> output) {
            if (output == null) {
                output = new HashSet<Entity>();
            } else {
                output.clear();
            }

            for (Entity entity : input) {
                if (this.type != null && this.type != entity.getType()) { // First filter type as that is probably fastest
                    continue;
                }
                if (this.type == EntityType.PLAYER) {
                    if (!(entity instanceof Player)) {
                        continue;
                    }
                    Player player = (Player) entity;
                    if (this.gamemode >= 0 && player.getGameMode().ordinal() != this.gamemode) {
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
                        if (this.inverseTeam) {
                            if (team == null && this.team.isEmpty()) {
                                continue;
                            }
                            if (team != null && this.team.equalsIgnoreCase(team.getName())) {
                                continue;
                            }
                        } else {
                            if (team == null && this.team.isEmpty()) {
                                continue;
                            }
                            if (team != null && !this.team.equalsIgnoreCase(team.getName())) {
                                continue;
                            }
                        }
                    }
                }

                if (this.name != null) {
                    String name = entity.getCustomName();
                    if (this.inverseName) {
                        if (name == null && this.team.isEmpty()) {
                            continue;
                        }
                        if (name != null && this.team.equalsIgnoreCase(name)) {
                            continue;
                        }
                    } else {
                        if (name == null && this.team.isEmpty()) {
                            continue;
                        }
                        if (name != null && !this.team.equalsIgnoreCase(name)) {
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
                        if (this.radiusMin > 0 && dX * dX + dY * dY + dZ * dZ < this.radiusMin) {
                            continue;
                        }
                        if (this.radiusMax > 0 && dX * dX + dY * dY + dZ * dZ > this.radiusMax) {
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
                TreeSet<Entity> filter = new TreeSet<Entity>(new Comparator<Entity>() {
                    @Override
                    public int compare(Entity o1, Entity o2) {
                        Location loc1 = o1.getLocation();
                        Location loc2 = o2.getLocation();
                        double dX1 = PlayerSelector.this.x - loc1.getX();
                        double dY1 = PlayerSelector.this.y - loc1.getY();
                        double dZ1 = PlayerSelector.this.z - loc1.getZ();
                        double dX2 = PlayerSelector.this.x - loc2.getX();
                        double dY2 = PlayerSelector.this.y - loc2.getY();
                        double dZ2 = PlayerSelector.this.z - loc2.getZ();
                        double dist1 = dX1 * dX1 + dY1 * dY1 + dZ1 * dZ1;
                        double dist2 = dX2 * dX2 + dY2 * dY2 + dZ2 * dZ2;
                        return (PlayerSelector.this.count > 0 ? 1 : 1) * Double.compare(dist1, dist2);
                    }
                });

                filter.addAll(output);
                output.clear();

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

        public void setGamemode(int gamemode) {
            this.gamemode = gamemode;
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

        public void setType(EntityType type) {
            this.type = type;
        }
    }

}
