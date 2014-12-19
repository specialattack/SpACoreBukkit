package net.specialattack.bukkit.core.games.team;

import org.bukkit.ChatColor;

/**
 * @author mbl111
 */
public enum TeamColors {

    BLACK(15, ChatColor.BLACK, 0x000000), //
    RED(14, ChatColor.DARK_RED, 0xFF0000), //
    GREEN(13, ChatColor.DARK_GREEN, 0x007800), //
    BLUE(11, ChatColor.DARK_BLUE, 0x0000FF), //
    PURPLE(10, ChatColor.DARK_PURPLE, 0x7F007F), //
    DARK_AQUA(9, ChatColor.DARK_AQUA, 0x007F7F), //
    LIGHT_GREY(8, ChatColor.GRAY, 0x7F7F7F), //
    DARK_GREY(7, ChatColor.DARK_GRAY, 0x3F3F3F), //
    LIME(5, ChatColor.GREEN, 0x00FF00), //
    YELLOW(4, ChatColor.YELLOW, 0xFFFF00), //
    LIGHT_PURPLE(2, ChatColor.LIGHT_PURPLE, 0xFF00FF), //
    ORANGE(1, ChatColor.GOLD, 0xFF7F00), //
    WHITE(0, ChatColor.WHITE, 0xFFFFFF);

    private int blockData;
    private ChatColor chatColor;
    private int armorColor;

    private TeamColors(int blockData, ChatColor chatColor, int armorColor) {
        this.blockData = blockData;
        this.chatColor = chatColor;
        this.armorColor = armorColor;
    }

    public int getBlockData() {
        return this.blockData;
    }

    public ChatColor getChatColor() {
        return this.chatColor;
    }

    public int getArmorColor() {
        return this.armorColor;
    }

}
