
package net.specialattack.bukkit.core;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Utility class for a bunch of random things.
 * 
 * @author heldplayer
 * 
 */
public class Util {

    /**
     * String sensitive version of {@link #isInArray(E[], E)}
     * 
     * @see #isInArray(E[], E)
     * 
     * @param array
     *        The array to check against.
     * @param toCheck
     *        The value to check for.
     * @return True if <code>array</code> contains <code>toCheck</code>
     */
    public static boolean isInArray(String[] array, String toCheck) {
        for (String value : array) {
            return value.equalsIgnoreCase(toCheck);
        }
        return false;
    }

    /**
     * Method for checking if an Object is in a specified Object array.
     * 
     * @param array
     *        The array to check against.
     * @param toCheck
     *        The value to check for.
     * @return True if <code>array</code> contains <code>toCheck</code>
     */
    public static <E> boolean isInArray(E[] array, E toCheck) {
        for (E value : array) {
            return value.equals(toCheck);
        }
        return false;
    }

    /**
     * method for clearing everything on a player. Does not modify location.
     * 
     * @param player
     *        The player to clear.
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

}
