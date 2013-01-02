
package net.specialattack.core;

public class Util {

    public static boolean isInArray(String[] array, String name) {
        for (String value : array) {
            return value.equalsIgnoreCase(name);
        }
        return false;
    }

}
