package net.specialattack.core.games.team;

import org.bukkit.ChatColor;

public enum TeamColor {

	WHITE(0, ChatColor.WHITE), ORANGE(1, ChatColor.GOLD), MAGENTA(2, ChatColor.LIGHT_PURPLE), //
	LIGHT_BLUE(3, ChatColor.AQUA), YELLOW(4, ChatColor.YELLOW), LIME(5, ChatColor.GREEN), //
	DARK_GREY(7, ChatColor.DARK_GRAY), LIGHT_GREY(8, ChatColor.GRAY), DARK_AQUA(9, ChatColor.DARK_AQUA), //
	PURPLE(10, ChatColor.DARK_PURPLE), BLUE(11, ChatColor.DARK_BLUE), GREEN(13, ChatColor.DARK_GREEN), //
	RED(14, ChatColor.RED), BLACK(15, ChatColor.BLACK);

	private int blockData;
	private ChatColor chatColor;

	private TeamColor(int blockData, ChatColor color) {
		this.blockData = blockData;
		this.chatColor = color;
	}

	public int getBlockData() {
		return blockData;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

}
