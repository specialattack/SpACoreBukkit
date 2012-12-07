
package net.specialattack.core.games;

import org.bukkit.ChatColor;

public enum TeamColors {

    BLACK(15, ChatColor.BLACK), //
    RED(14, ChatColor.DARK_RED), //
    GREEN(13, ChatColor.DARK_GREEN), //
    BLUE(11, ChatColor.DARK_BLUE), //
    PURPLE(10, ChatColor.DARK_PURPLE), //
    DARK_AQUA(9, ChatColor.DARK_AQUA), //
    LIGHT_GREY(8, ChatColor.GRAY), //
    DARK_GREY(7, ChatColor.DARK_GRAY), //
    LIME(5, ChatColor.GREEN), //
    YELLOW(4, ChatColor.YELLOW), //
    LIGHT_PURPLE(2, ChatColor.LIGHT_PURPLE), //
    ORANGE(1, ChatColor.GOLD), //
    WHITE(0, ChatColor.WHITE);

    private int blockData;
    private ChatColor chatColor;

    private TeamColors(int blockData, ChatColor chatColor) {
        this.blockData = blockData;
        this.chatColor = chatColor;
    }

    public int getBlockData() {
        return blockData;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

}
